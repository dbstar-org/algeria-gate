package io.github.dbstarll.algeria.boot.model.api.request.user;

import io.github.dbstarll.algeria.boot.model.api.request.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class QueryUserRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -4041465202829007907L;

    @NotBlank
    @Schema(description = "操作员类型")
    private String role;

    @NotBlank
    @Schema(description = "操作员ID")
    private String roleCode;

    @NotBlank
    @Schema(description = "用户的电话号码")
    private String phoneNumber;

    @Schema(description = "用户状态")
    private String status;
}
