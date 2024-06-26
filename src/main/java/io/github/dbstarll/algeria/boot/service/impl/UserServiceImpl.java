package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.error.FrequentlyObtainVerifyCodeException;
import io.github.dbstarll.algeria.boot.model.service.VerifyCodeTimeData;
import io.github.dbstarll.algeria.boot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private static final Duration VERIFY_CODE_INTERVAL = Duration.ofMinutes(1);
    private static final Duration VERIFY_CODE_DURATION = Duration.ofMinutes(5);

    private final RbtApi rbtApi;
    private final SecureRandom secureRandom;

    private final ConcurrentMap<String, VerifyCodeTimeData> verifyCodes = new ConcurrentHashMap<>();

    @Override
    public void verifyCode(final String mobile) {
        verifyCodes.compute(mobile, (k, v) -> {
            if (v == null || v.expire(VERIFY_CODE_INTERVAL)) {
                final String code = RandomStringUtils.random(6, 0, 0, false, true, null, secureRandom);
                return new VerifyCodeTimeData(code);
            } else {
                throw new FrequentlyObtainVerifyCodeException(mobile);
            }
        });
    }
}
