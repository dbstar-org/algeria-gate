package io.github.dbstarll.algeria.boot.model.api.response;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponse extends BaseModel {
    private static final long serialVersionUID = 8730985039155524776L;

    @Schema(description = "返回码")
    private String returnCode;
}
