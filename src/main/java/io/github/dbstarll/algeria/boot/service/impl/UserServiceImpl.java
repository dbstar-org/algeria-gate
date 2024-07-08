package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.error.InvalidAccessTokenException;
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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private static final Duration SESSION_EXPIRE = Duration.ofMinutes(30);
    private static final FastDateFormat FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

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
    public boolean isSubscribe(final SessionTimeData session) {
        return session.getTones().stream()
                .filter(t -> toneService.exists(t.getToneID()))
                .anyMatch(t -> before(t.getAvailableDateTime()));
    }

    private boolean before(final String time) {
        return FORMAT.format(System.currentTimeMillis()).compareTo(time) <= 0;
    }
}
