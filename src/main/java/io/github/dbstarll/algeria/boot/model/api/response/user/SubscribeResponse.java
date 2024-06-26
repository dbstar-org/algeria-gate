package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.api.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class SubscribeResponse extends BaseResponse {
    private static final long serialVersionUID = -4374459592291029819L;

    @Schema(description = "The operation id")
    private String operationID;

    @Schema(description = "The resultCode")
    private String resultCode;

    @Schema(description = "结果信息")
    private String resultInfo;

    @Schema(description = "The transaction ID")
    private String transactionID;
}
