package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.error.InvalidAccessTokenException;
import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.ProductInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.UserProductInfo;
import io.github.dbstarll.algeria.boot.model.service.SessionTimeData;
import io.github.dbstarll.algeria.boot.service.ToneService;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.github.dbstarll.utils.net.api.ApiException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private static final Duration SESSION_EXPIRE = Duration.ofMinutes(30);
    private static final FastDateFormat FORMAT_TIME = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private static final FastDateFormat FORMAT_DAY = FastDateFormat.getInstance("yyyy-MM-dd");

    private final RbtApi rbtApi;
    private final ToneService toneService;

    private final ConcurrentMap<UUID, SessionTimeData> sessions = new ConcurrentHashMap<>();

    @Override
    public UUID login(final String phone) throws IOException, ApiException {
        final UUID token = Uuid.generate();
        sessions.put(token, new SessionTimeData(phone,
                rbtApi.user().queryUser(phone).getUserInfos(),
                rbtApi.user().queryUserProduct(phone).getUserProductInfos(),
                rbtApi.user().tone().queryInboxTone(phone).getToneInfos()
        ));
        return token;
    }

    @Override
    public SessionTimeData verify(final UUID token, final boolean renew) {
        return sessions.compute(token, (k, v) -> {
            if (v == null || v.expire(SESSION_EXPIRE)) {
                throw new InvalidAccessTokenException("Invalid Access-Token");
            } else {
                if (renew) {
                    v.renew();
                }
                return v;
            }
        });
    }

    @Override
    public void update(final UUID token) throws IOException, ApiException {
        final String phone = verify(token, false).getPhone();
        sessions.put(token, new SessionTimeData(phone,
                rbtApi.user().queryUser(phone).getUserInfos(),
                rbtApi.user().queryUserProduct(phone).getUserProductInfos(),
                rbtApi.user().tone().queryInboxTone(phone).getToneInfos()
        ));
    }

    @Override
    public SessionTimeData logout(final UUID token) {
        return sessions.remove(token);
    }

    @Override
    public boolean isSubscribe(final SessionTimeData session, final boolean vip) {
        if (isVip(session)) {
            return true;
        } else if (vip) {
            return false;
        } else {
            return isSubscribe(session);
        }
    }

    private boolean isSubscribe(final SessionTimeData session) {
        return Optional.ofNullable(session.getTones())
                .map(ps -> ps.stream().anyMatch(this::isSubscribe))
                .orElse(false);
    }

    private boolean isSubscribe(final ToneInfo tone) {
        return toneService.exists(tone.getToneID()) && beforeTime(tone.getAvailableDateTime());
    }

    private boolean isVip(final SessionTimeData session) {
        return Optional.ofNullable(session.getProducts())
                .map(ps -> ps.stream().anyMatch(this::isVip))
                .orElse(false);
    }

    private boolean isVip(final UserProductInfo product) {
        return Optional.ofNullable(product.getProductinfos())
                .map(ps -> ps.stream().anyMatch(this::isVip))
                .orElse(false);
    }

    private boolean isVip(final ProductInfo product) {
        return beforeDay(product.getMonthFeeEndDate());
    }

    private boolean beforeTime(final String time) {
        return FORMAT_TIME.format(System.currentTimeMillis()).compareTo(time) <= 0;
    }

    private boolean beforeDay(final String day) {
        return FORMAT_DAY.format(System.currentTimeMillis()).compareTo(day) <= 0;
    }
}
