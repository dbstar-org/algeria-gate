package io.github.dbstarll.algeria.boot.model.api.request.user;

import io.github.dbstarll.algeria.boot.model.api.request.BaseUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class SubscribeRequest extends BaseUpdateRequest {
    private static final long serialVersionUID = -8605529909968525857L;

    @NotBlank
    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "Language ID")
    private String languageID;

    @Schema(description = "Transaction ID")
    private String transactionID;
}
