package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.github.dbstarll.algeria.boot.model.api.request.QueryCatalogToneRequest;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiClient;
import io.github.dbstarll.utils.net.api.ApiException;
import org.apache.hc.client5.http.classic.HttpClient;

import java.io.IOException;

public final class RbtApi extends JsonApiClient {
    public RbtApi(final HttpClient httpClient, ObjectMapper mapper, final RbtApiSettings settings) {
        super(httpClient, true, optimize(mapper));
        setUriResolver(new RelativeUriResolver(settings.getUri(), settings.getContext()));
    }

    private static ObjectMapper optimize(final ObjectMapper mapper) {
        return mapper
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public JsonNode queryCatalogTone() throws IOException, ApiException {
        final QueryCatalogToneRequest request = new QueryCatalogToneRequest();
        return execute(post("/toneprovide/querycatalogtone")
                .setEntity(jsonEntity(request)).build(), JsonNode.class);
    }
}
