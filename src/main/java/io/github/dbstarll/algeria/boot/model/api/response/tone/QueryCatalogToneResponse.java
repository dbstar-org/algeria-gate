package io.github.dbstarll.algeria.boot.model.api.response.tone;

import io.github.dbstarll.algeria.boot.model.api.response.BaseQueryResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class QueryCatalogToneResponse extends BaseQueryResponse {
    private static final long serialVersionUID = -6565940692338805547L;

    private List<ToneInfo> toneInfos;
}
