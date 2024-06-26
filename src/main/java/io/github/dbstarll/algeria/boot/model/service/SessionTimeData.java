package io.github.dbstarll.algeria.boot.model.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public final class SessionTimeData extends TimeData {
    private static final long serialVersionUID = -8811885361087045773L;

    private final String phone;

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner).add("phone=" + getPhone());
    }
}
