package io.github.dbstarll.algeria.boot.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.error.InvalidAccessTokenException;
import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.ProductInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.UserProductInfo;
import io.github.dbstarll.algeria.boot.model.service.Session;
import io.github.dbstarll.algeria.boot.service.ToneService;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.github.dbstarll.utils.lang.wrapper.EntryWrapper;
import io.github.dbstarll.utils.net.api.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private static final Duration SESSION_EXPIRE = Duration.ofMinutes(30);
    private static final FastDateFormat FORMAT_TIME = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    private static final FastDateFormat FORMAT_DAY = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final String CACHE_KEY_PREFIX = "session:login:access-token:";

    private final RbtApi rbtApi;
    private final ToneService toneService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;
    private final ObjectMapper mapper;

    private String cacheKey(final UUID token) {
        return CACHE_KEY_PREFIX + Uuid.toString(token);
    }

    private String putToCache(final Session session) {
        return mapper.valueToTree(session).toString();
    }

    private Optional<Session> getFromCache(final String cacheKey) {
        return Optional.of(cacheKey)
                .map(valueOperations::get)
                .map(s -> {
                    try {
                        return mapper.readValue(s, Session.class);
                    } catch (JsonProcessingException e) {
                        log.warn("解析Session失败：", e);
                        return null;
                    }
                });
    }

    @Override
    public Map.Entry<UUID, Session> login(final String phone) throws IOException, ApiException {
        final UUID token = Uuid.generate();
        return EntryWrapper.wrap(token, putToCache(token, phone));
    }

    @Override
    public Session update(final UUID token) throws IOException, ApiException {
        final String phone = verify(token, false).getPhone();
        return putToCache(token, phone);
    }

    private Session putToCache(final UUID token, final String phone) throws IOException, ApiException {
        final Session session = new Session(phone,
                rbtApi.user().queryUser(phone).getUserInfos(),
                rbtApi.user().queryUserProduct(phone).getUserProductInfos(),
                rbtApi.user().tone().queryInboxTone(phone).getToneInfos()
        );
        valueOperations.set(cacheKey(token), putToCache(session), SESSION_EXPIRE);
        return session;
    }

    @Override
    public Session verify(final UUID token, final boolean renew) {
        final String cacheKey = cacheKey(token);
        return getFromCache(cacheKey).map(session -> {
            if (renew) {
                redisTemplate.expire(cacheKey, SESSION_EXPIRE);
            }
            return session;
        }).orElseThrow(() -> new InvalidAccessTokenException("Invalid Access-Token"));
    }

    @Override
    public Session logout(final UUID token) {
        final Session session = verify(token, false);
        redisTemplate.delete(cacheKey(token));
        return session;
    }

    @Override
    public boolean isSubscribe(final Session session, final boolean vip) {
        if (isVip(session)) {
            return true;
        } else if (vip) {
            return false;
        } else {
            return isSubscribe(session);
        }
    }

    private boolean isSubscribe(final Session session) {
        return Optional.ofNullable(session.getTones())
                .map(ps -> ps.stream().anyMatch(this::isSubscribe))
                .orElse(false);
    }

    private boolean isSubscribe(final ToneInfo tone) {
        return toneService.exists(tone.getToneID()) && beforeTime(tone.getAvailableDateTime());
    }

    private boolean isVip(final Session session) {
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
