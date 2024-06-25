package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dbstarll.algeria.boot.model.api.request.BaseRequest;
import io.github.dbstarll.algeria.boot.model.api.request.system.SendSmRequest;
import io.github.dbstarll.algeria.boot.model.api.request.tone.GetFileRequest;
import io.github.dbstarll.algeria.boot.model.api.request.tone.QueryCatalogToneRequest;
import io.github.dbstarll.algeria.boot.model.api.request.user.QueryInboxToneRequest;
import io.github.dbstarll.algeria.boot.model.api.request.user.QueryUserProductRequest;
import io.github.dbstarll.algeria.boot.model.api.request.user.QueryUserRequest;
import io.github.dbstarll.algeria.boot.model.api.response.BaseResponse;
import io.github.dbstarll.algeria.boot.model.api.response.system.SendSmResponse;
import io.github.dbstarll.algeria.boot.model.api.response.tone.QueryCatalogToneResponse;
import io.github.dbstarll.algeria.boot.model.api.response.user.QueryInboxToneResponse;
import io.github.dbstarll.algeria.boot.model.api.response.user.QueryUserProductResponse;
import io.github.dbstarll.algeria.boot.model.api.response.user.QueryUserResponse;
import io.github.dbstarll.utils.http.client.request.RelativeUriResolver;
import io.github.dbstarll.utils.json.jackson.JsonApiClient;
import io.github.dbstarll.utils.net.api.ApiException;
import io.github.dbstarll.utils.net.api.ApiProtocolException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.springframework.data.util.Predicates;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

public final class RbtApi extends JsonApiClient {
    private final RbtApiSettings settings;
    private final ToneProvide toneProvide;
    private final UserManage userManage;
    private final System system;

    public RbtApi(final HttpClient httpClient, ObjectMapper mapper, final RbtApiSettings settings) {
        super(httpClient, true, optimize(mapper));
        this.settings = settings;
        setUriResolver(new RelativeUriResolver(settings.getUri(), settings.getContext()));
        this.toneProvide = new ToneProvide("/toneprovide");
        this.userManage = new UserManage("/usermanage");
        this.system = new System("/system");
    }

    public ToneProvide tone() {
        return toneProvide;
    }

    public UserManage user() {
        return userManage;
    }

    public System system() {
        return system;
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

    private <T extends BaseRequest> HttpEntity auth(final T request, final Consumer<T> consumer)
            throws JsonProcessingException {
        request.setPortalAccount(settings.getPortalAccount());
        request.setPortalPwd(settings.getPortalPwd());
        request.setPortalType(settings.getTone().getPortalType());
        consumer.accept(request);
        return jsonEntity(request);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class ToneProvide {
        private final String moduleRoot;

        public QueryCatalogToneResponse query(final String status) throws IOException, ApiException {
            return execute(post(moduleRoot + "/querycatalogtone")
                    .setEntity(auth(new QueryCatalogToneRequest(), request -> {
                        request.setCatalogID(settings.getTone().getCatalogId());
                        request.setResourceType("1");
                        request.setStatus(status);
                    })).build(), QueryCatalogToneResponse.class);
        }

        public byte[] get(final String resourceId) throws IOException, ApiException {
            return execute(post(moduleRoot + "/getfile").setEntity(auth(new GetFileRequest(),
                    request -> request.setResourceID(resourceId))).build(), byte[].class);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class UserManage {
        private final String moduleRoot;

        private final UserToneManage userToneManage = new UserToneManage("usertonemanage");

        public UserToneManage tone() {
            return userToneManage;
        }

        public QueryUserResponse queryUser(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/queryuser").setEntity(auth(new QueryUserRequest(),
                    request -> request.setPhoneNumber(phone))).build(), QueryUserResponse.class);
        }

        public QueryUserProductResponse queryUserProduct(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/queryuserproduct")
                    .setEntity(auth(new QueryUserProductRequest(),
                            request -> request.setPhoneNumber(phone))).build(), QueryUserProductResponse.class);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class UserToneManage {
        private final String moduleRoot;

        public QueryInboxToneResponse queryInboxTone(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/queryinboxtone").setEntity(auth(new QueryInboxToneRequest(),
                    request -> {
                        request.setPhoneNumber(phone);
                        request.setResourceType("1");
                    })).build(), QueryInboxToneResponse.class);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class System {
        private final String moduleRoot;

        public SendSmResponse sendSm(final String phone, final String code) throws IOException, ApiException {
            return execute(post(moduleRoot + "/sendsm").setEntity(auth(new SendSmRequest(), request -> {
                request.setRole(settings.getRole());
                request.setRoleCode(settings.getRoleCode());
                request.setPhoneNumbers(new String[]{phone});
                request.setPlaceHolderParams(new String[]{code});
            })).build(), SendSmResponse.class);
        }
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
