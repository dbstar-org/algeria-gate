package io.github.dbstarll.algeria.boot.model.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public final class QueryCatalogToneRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 3478401668000994677L;

    public static final String DEFAULT_ORDER_TYPE = "1";

    @NotBlank
    @Schema(description = "分类ID")
    private String catalogID;

    @Schema(description = "排序模式 - 1：升序;2：降序")
    private String orderType = DEFAULT_ORDER_TYPE;

    @NotBlank
    @Schema(description = "Resource type")
    private String resourceType;

    @Schema(description = "上传RBT的类型，可以为null。此参数中传递的多个值应以#分隔。最大长度为10。")
    private String uploadType;

    @Schema(description = "Tone state")
    private String status;
}
