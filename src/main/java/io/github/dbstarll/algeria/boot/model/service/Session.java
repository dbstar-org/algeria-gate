package io.github.dbstarll.algeria.boot.model.service;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.UserInfo;
import io.github.dbstarll.algeria.boot.model.api.response.user.UserProductInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.StringJoiner;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Session extends BaseModel {
    private static final long serialVersionUID = -8811885361087045773L;

    private String phone;
    private List<UserInfo> users;
    private List<UserProductInfo> products;
    private List<ToneInfo> tones;

    @Override
    protected StringJoiner addToStringEntry(final StringJoiner joiner) {
        return super.addToStringEntry(joiner).add("phone=" + getPhone());
    }
}
