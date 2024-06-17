package io.github.dbstarll.algeria.boot.model.api.request;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public abstract class BaseRequest extends BaseModel {
    private static final long serialVersionUID = 5171124652008959460L;

    public static final String DEFAULT_PORTAL_TYPE = "99";

    @NotBlank
    @Schema(description = "外部用户访问USDP的帐号")
    private String portalAccount;

    @NotBlank
    @Schema(description = "外部用户访问USDP的密码")
    private String portalPwd;

    @NotBlank
    @Schema(description = "门户类型")
    private String portalType = DEFAULT_PORTAL_TYPE;
}
