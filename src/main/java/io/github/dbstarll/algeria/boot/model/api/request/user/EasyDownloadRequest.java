package io.github.dbstarll.algeria.boot.model.api.request.user;

import io.github.dbstarll.algeria.boot.model.api.request.BaseUpdateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class EasyDownloadRequest extends BaseUpdateRequest {
    private static final long serialVersionUID = 6605734139753193273L;

    @NotBlank
    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "Language ID")
    private String languageID;

    @Schema(description = "Transaction ID")
    private String transactionID;

    @Schema(description = "RBT type. 1：普通铃音;2：铃音盒")
    private String resourceType;

    @Schema(description = "铃音或铃音盒的内部ID")
    private String[] resourceID;

    @Schema(description = "铃音或铃音盒的编码")
    private String[] resourceCode;
}
