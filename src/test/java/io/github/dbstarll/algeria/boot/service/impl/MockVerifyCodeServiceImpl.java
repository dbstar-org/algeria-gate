package io.github.dbstarll.algeria.boot.service.impl;

import io.github.dbstarll.algeria.boot.service.VerifyCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
final class MockVerifyCodeServiceImpl implements VerifyCodeService {

    @Override
    public String generate() {
        return "000000";
    }
}
