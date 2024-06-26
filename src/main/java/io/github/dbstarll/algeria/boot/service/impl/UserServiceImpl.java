package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.error.FrequentlyObtainVerifyCodeException;
import io.github.dbstarll.algeria.boot.error.InvalidVerifyCodeException;
import io.github.dbstarll.algeria.boot.model.service.SessionTimeData;
import io.github.dbstarll.algeria.boot.model.service.VerifyCodeTimeData;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.github.dbstarll.algeria.boot.service.VerifyCodeService;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.github.dbstarll.utils.net.api.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private static final Duration VERIFY_CODE_INTERVAL = Duration.ofMinutes(1);
    private static final Duration VERIFY_CODE_DURATION = Duration.ofMinutes(5);

    private final RbtApi rbtApi;
    private final VerifyCodeService verifyCodeService;

    private final ConcurrentMap<String, VerifyCodeTimeData> verifyCodes = new ConcurrentHashMap<>();
    private final ConcurrentMap<UUID, SessionTimeData> sessions = new ConcurrentHashMap<>();

    @Override
    public void verifyCode(final String phone) throws IOException, ApiException {
        rbtApi.system().sendSm(phone, verifyCodes.compute(phone, (k, v) -> {
            if (v == null || v.expire(VERIFY_CODE_INTERVAL)) {
                return new VerifyCodeTimeData(verifyCodeService.generate());
            } else {
                throw new FrequentlyObtainVerifyCodeException(phone);
            }
        }).getVerifyCode());
    }

    @Override
    public UUID login(final String phone, final String verifyCode) {
        verifyCodes.compute(phone, (k, v) -> {
            if (v == null || v.expire(VERIFY_CODE_DURATION) || !verifyCode.equals(v.getVerifyCode())) {
                throw new InvalidVerifyCodeException("验证码错误");
            } else {
                return v;
            }
        });
        final UUID token = Uuid.generate();
        sessions.put(token, new SessionTimeData(phone));
        return token;
    }
}
