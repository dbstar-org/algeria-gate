package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.api.response.BaseQueryResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class QueryUserResponse extends BaseQueryResponse {
    private static final long serialVersionUID = -6251704435007613734L;

    private List<UserInfo> userInfos;
}
