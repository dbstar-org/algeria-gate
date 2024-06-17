package io.github.dbstarll.algeria.boot.model.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public abstract class BaseUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 686289125326993275L;

    public static final String DEFAULT_MODULE_CODE = "000000";
    public static final String DEFAULT_ROLE = "1";

    @Schema(description = "Module code")
    private String moduleCode = DEFAULT_MODULE_CODE;

    @NotBlank
    @Schema(description = "操作员类型")
    private String role = DEFAULT_ROLE;

    @NotBlank
    @Schema(description = "操作员代码")
    private String roleCode;
}
