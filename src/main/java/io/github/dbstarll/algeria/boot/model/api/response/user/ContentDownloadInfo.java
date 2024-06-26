package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ContentDownloadInfo extends BaseModel {
    private static final long serialVersionUID = -4737897431438473741L;

    @Schema(description = "RBT类型")
    private String resourceType;

    @Schema(description = "RBT内部ID")
    private String resourceId;

    @Schema(description = "RBT长编码")
    private String resourceCode;

    @Schema(description = "下载内容的费用")
    private String chargedPrice;

    @Schema(description = "计费时间")
    private String chargeTime;

    @Schema(description = "资源内容的相对有效期时长")
    private String duration;

    @Schema(description = "下载内容的相对失效日期")
    private String relativeExpiryDate;
}
