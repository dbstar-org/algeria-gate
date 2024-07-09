package io.github.dbstarll.algeria.boot.model.api.request.user;

import io.github.dbstarll.algeria.boot.model.api.request.BaseUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public final class SubscribeProductRequest extends BaseUpdateRequest {
    private static final long serialVersionUID = 1914450164443167722L;

    @NotNull
    @Schema(description = "手机号码数组字符串")
    private String[] phoneNumbers;

    @NotBlank
    @Schema(description = "产品ID")
    private String productID;

    @Schema(description = "Transaction ID")
    private String transactionID;
}
