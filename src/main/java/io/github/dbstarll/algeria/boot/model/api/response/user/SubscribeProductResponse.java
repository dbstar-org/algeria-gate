package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.api.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class SubscribeProductResponse extends BaseResponse {
    private static final long serialVersionUID = 7201025461169625624L;

    @Schema(description = "返回结果信息")
    private List<SubscribeProduct> returnObjects;
}
