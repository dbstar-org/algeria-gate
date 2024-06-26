package io.github.dbstarll.algeria.boot.model.api.request.user;

import io.github.dbstarll.algeria.boot.model.api.request.BaseUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class DelInboxToneRequest extends BaseUpdateRequest {
    private static final long serialVersionUID = 7878635187201446193L;

    @Schema(description = "音频类型数组字符串\t字符串数组")
    private String resourceType;

    @NotBlank
    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "资源ID")
    private String resourceID;

    @Schema(description = "铃音编码")
    private String resourceCode;

    @Schema(description = "The transaction ID")
    private String transactionID;
}
