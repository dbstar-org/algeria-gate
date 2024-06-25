package io.github.dbstarll.algeria.boot.model.api.request.user;

import io.github.dbstarll.algeria.boot.model.api.request.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class QueryInboxToneRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -6204472883183590588L;

    @NotBlank
    @Schema(description = "音频类型")
    private String resourceType;

    @NotBlank
    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "内容的状态")
    private String status;

    @Schema(description = "查询记录的排序顺序")
    private String orderType;

    @Schema(description = "查询记录的排序方式")
    private String orderBy;
}
