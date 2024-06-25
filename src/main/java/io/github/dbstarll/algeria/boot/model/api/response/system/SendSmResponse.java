package io.github.dbstarll.algeria.boot.model.api.response.system;

import io.github.dbstarll.algeria.boot.model.api.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class SendSmResponse extends BaseResponse {
    private static final long serialVersionUID = 4479888677551969431L;

    @Schema(description = "The operation id")
    private String operationID;

    @Schema(description = "The resultCode")
    private String resultCode;
}
