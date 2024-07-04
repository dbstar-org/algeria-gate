package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.error.FrequentlyObtainVerifyCodeException;
import io.github.dbstarll.algeria.boot.error.InvalidAccessTokenException;
import io.github.dbstarll.algeria.boot.model.service.SessionTimeData;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.github.dbstarll.algeria.boot.service.VerifyCodeService;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.github.dbstarll.utils.net.api.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    private static final String CACHE_KEY_PREFIX = "phone:verify-code:number:";
    private static final String CACHE_SIGN_KEY_PREFIX = "phone:verify-code:sign:";
    private static final Duration VERIFY_CODE_INTERVAL = Duration.ofMinutes(1);
    private static final Duration VERIFY_CODE_EXPIRE = Duration.ofMinutes(5);
    private static final Duration SESSION_EXPIRE = Duration.ofMinutes(30);

    private final RbtApi rbtApi;
    private final VerifyCodeService verifyCodeService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    private final ConcurrentMap<UUID, SessionTimeData> sessions = new ConcurrentHashMap<>();

    private String cacheKey(final String phone) {
        return CACHE_KEY_PREFIX + phone;
    }

    private String cacheWaitKey(final String phone) {
        return CACHE_KEY_PREFIX + phone + "wait";
    }

    private String cacheSignKey(final String sign) {
        return CACHE_SIGN_KEY_PREFIX + sign;
    }

    @Override
    public long obtainVerifyCode(final String phone) throws IOException, ApiException {
        final String cacheWaitKey = cacheWaitKey(phone);
        if (valueOperations.get(cacheWaitKey) != null) {
            throw new FrequentlyObtainVerifyCodeException(phone);
        }
        final String code = verifyCodeService.generate();
        rbtApi.system().sendSm(phone, code);
        valueOperations.set(cacheKey(phone), code, VERIFY_CODE_EXPIRE);
        valueOperations.set(cacheWaitKey, "code", VERIFY_CODE_INTERVAL);
        return VERIFY_CODE_INTERVAL.getSeconds();
    }

    @Override
    public Optional<String> verification(final String phone, final String verifyCode) {
        final String cacheKey = cacheKey(phone);
        try {
            if (verifyCode.equals(valueOperations.get(cacheKey))) {
                final String sign = Uuid.toString(Uuid.generate());
                valueOperations.set(cacheSignKey(sign), phone, VERIFY_CODE_EXPIRE);
                return Optional.of(sign);
            } else {
                return Optional.empty();
            }
        } finally {
            redisTemplate.delete(cacheKey);
        }
    }

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
}
