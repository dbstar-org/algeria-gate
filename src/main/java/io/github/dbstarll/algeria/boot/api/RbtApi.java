package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dbstarll.algeria.boot.model.api.request.QueryCatalogToneRequest;
import io.github.dbstarll.algeria.boot.model.api.response.BaseResponse;
import io.github.dbstarll.algeria.boot.model.api.response.QueryCatalogToneResponse;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiClient;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.net.api.ApiProtocolException;
import lombok.Getter;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.springframework.data.util.Predicates;

import java.io.IOException;
import java.util.Optional;

public final class RbtApi extends JsonApiClient {
    private final RbtApiSettings settings;

    public RbtApi(final HttpClient httpClient, ObjectMapper mapper, final RbtApiSettings settings) {
        super(httpClient, true, optimize(mapper));
        this.settings = settings;
        setUriResolver(new RelativeUriResolver(settings.getUri(), settings.getContext()));
    }

    private static ObjectMapper optimize(final ObjectMapper mapper) {
        return mapper
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    protected <T> T postProcessing(ClassicHttpRequest request, T executeResult) throws ApiException {
        final T finalResult = super.postProcessing(request, executeResult);
        final Optional<RbtApiException> optException = Optional.ofNullable(finalResult)
                .filter(ObjectNode.class::isInstance).map(ObjectNode.class::cast)
                .map(node -> node.get("returnCode")).map(JsonNode::textValue)
                .filter(Predicates.negate(BaseResponse.RETURN_CODE_SUCCESS::equals))
                .map(RbtApiException::new);
        if (optException.isPresent()) {
            throw optException.get();
        }
        return finalResult;
    }

    public QueryCatalogToneResponse queryCatalogTone(final String status) throws IOException, ApiException {
        final QueryCatalogToneRequest request = new QueryCatalogToneRequest();
        request.setPortalAccount(settings.getPortalAccount());
        request.setPortalPwd(settings.getPortalPwd());
        request.setPortalType(settings.getTone().getPortalType());
        request.setCatalogId(settings.getTone().getCatalogId());
        request.setResourceType("1");
        request.setStatus(status);
        return execute(post("/toneprovide/querycatalogtone").setEntity(jsonEntity(request)).build(),
                QueryCatalogToneResponse.class);
    }

    @Getter
    public static final class RbtApiException extends ApiProtocolException {
        private static final long serialVersionUID = 4948918675208623272L;

        private final String returnCode;

        public RbtApiException(String returnCode) {
            super(String.format("RBT call failed[%s]", returnCode), null);
            this.returnCode = returnCode;
        }
    }
}
