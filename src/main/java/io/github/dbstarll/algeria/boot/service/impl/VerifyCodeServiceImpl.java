package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.component.AlgeriaGateProperties;
import io.github.dbstarll.algeria.boot.service.VerifyCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
final class VerifyCodeServiceImpl implements VerifyCodeService {
    private static final int VERIFY_CODE_LENGTH = 6;

    private final AlgeriaGateProperties algeriaGateProperties;
    private final SecureRandom secureRandom;

    @Override
    public String generate() {
        return Optional.ofNullable(algeriaGateProperties.getVerifyCode())
                .filter(StringUtils::isNotBlank)
                .orElseGet(() -> RandomStringUtils.random(VERIFY_CODE_LENGTH, 0, 0, false, true, null, secureRandom));
    }
}
