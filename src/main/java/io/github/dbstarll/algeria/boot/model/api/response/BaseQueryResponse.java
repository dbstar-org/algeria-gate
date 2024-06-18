package io.github.dbstarll.algeria.boot.model.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseQueryResponse extends BaseResponse {
    private static final long serialVersionUID = -5423110934092726669L;

    @Schema(description = "返回结果集的数量")
    private String recordSum;
}
