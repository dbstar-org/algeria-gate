package io.github.dbstarll.algeria.boot.model.api.response.user;

import io.github.dbstarll.algeria.boot.model.api.response.BaseQueryResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class QueryUserProductResponse extends BaseQueryResponse {
    private static final long serialVersionUID = -2787653572321340575L;

    private List<UserProductInfo> userProductInfos;
}
