package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.api.response.BaseQueryResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class QueryUserProductResponse extends BaseQueryResponse {
    private static final long serialVersionUID = -2787653572321340575L;

    @Schema(description = "用户产品信息")
    private List<UserProductInfo> userProductInfos;
}
