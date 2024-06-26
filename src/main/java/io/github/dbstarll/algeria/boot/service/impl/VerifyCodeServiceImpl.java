package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.service.VerifyCodeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
final class VerifyCodeServiceImpl implements VerifyCodeService {
    private final SecureRandom secureRandom;

    @Override
    public String generate() {
        return RandomStringUtils.random(6, 0, 0, false, true, null, secureRandom);
    }
}
