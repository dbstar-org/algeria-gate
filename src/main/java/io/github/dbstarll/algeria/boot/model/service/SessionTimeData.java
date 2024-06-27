package io.github.dbstarll.algeria.boot.model.service;

import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.UserInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.UserProductInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public final class SessionTimeData extends TimeData {
    private static final long serialVersionUID = -8811885361087045773L;

    private final String phone;
    private final List<UserInfo> users;
    private final List<UserProductInfo> products;
    private final List<ToneInfo> tones;

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner).add("phone=" + getPhone());
    }
}
