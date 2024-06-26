package io.github.dbstarll.algeria.boot.model.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public final class VerifyCodeTimeData extends TimeData {
    private static final long serialVersionUID = -2385652174844614320L;

    private final String verifyCode;

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner).add("verifyCode=" + getVerifyCode());
    }
}
