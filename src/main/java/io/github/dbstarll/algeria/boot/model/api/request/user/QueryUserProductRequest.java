package io.github.dbstarll.algeria.boot.model.api.request.user;

import io.github.dbstarll.algeria.boot.model.api.request.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class QueryUserProductRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -5322510938139363317L;

    @NotBlank
    @Schema(description = "用户的电话号码")
    private String phoneNumber;

    @Schema(description = "用户状态")
    private String status;

    @Schema(description = "product code")
    private String productID;
}
