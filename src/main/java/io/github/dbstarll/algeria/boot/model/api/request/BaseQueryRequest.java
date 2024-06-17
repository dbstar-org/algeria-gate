package io.github.dbstarll.algeria.boot.model.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseQueryRequest extends BaseRequest {
    private static final long serialVersionUID = 8361863873288955699L;

    public static final String DEFAULT_START_RECORD_NUM = "1";
    public static final String DEFAULT_END_RECORD_NUM = "50";
    public static final String DEFAULT_QUERY_TYPE = "2";

    @Schema(description = "开始记录标识符")
    private String startRecordNum = DEFAULT_START_RECORD_NUM;

    @Schema(description = "结束记录标识符")
    private String endRecordNum = DEFAULT_END_RECORD_NUM;

    @Schema(description = "返回结果的类型")
    private String queryType = DEFAULT_QUERY_TYPE;
}
