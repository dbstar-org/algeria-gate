package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.api.response.BaseQueryResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class QueryUserResponse extends BaseQueryResponse {
    private static final long serialVersionUID = -6251704435007613734L;

    @Schema(description = "The operation id")
    private String operationID;

    @Schema(description = "The resultCode")
    private String resultCode;

    private UserInfo[] userInfos;
}
