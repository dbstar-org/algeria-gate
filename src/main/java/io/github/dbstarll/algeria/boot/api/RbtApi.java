package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiClient;
import org.apache.hc.client5.http.classic.HttpClient;

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
}
