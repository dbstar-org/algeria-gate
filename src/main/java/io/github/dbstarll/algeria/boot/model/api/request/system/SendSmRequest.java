package io.github.dbstarll.algeria.boot.model.api.request.system;

import io.github.dbstarll.algeria.boot.model.api.request.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class SendSmRequest extends BaseRequest {
    private static final long serialVersionUID = -1876042325302069312L;

    private static final String DEFAULT_TIME_TYPE = "2"; // 实时发送
    private static final String DEFAULT_SM_LABEL = "sm_send_game_verification_code";

    @NotBlank
    @Schema(description = "操作员类型")
    private String role;

    @NotBlank
    @Schema(description = "操作员ID")
    private String roleCode;

    @NotBlank
    @Schema(description = "手机号码数组字符串")
    private String[] phoneNumbers;

    @NotBlank
    @Schema(description = "是否实时发送短信")
    private String timeType = DEFAULT_TIME_TYPE;

    @Schema(description = "短信模板名")
    private String smLabel = DEFAULT_SM_LABEL;

    @Schema(description = "短信模板参数数组字符串")
    private String[] placeHolderParams;
}
