package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dbstarll.algeria.boot.model.api.request.BaseRequest;
import io.github.dbstarll.algeria.boot.model.api.request.BaseUpdateRequest;
import io.github.dbstarll.algeria.boot.model.api.request.system.SendSmRequest;
import io.github.dbstarll.algeria.boot.model.api.request.tone.GetFileRequest;
import io.github.dbstarll.algeria.boot.model.api.request.tone.QueryCatalogToneRequest;
import io.github.dbstarll.algeria.boot.model.api.request.user.*;
import io.github.dbstarll.algeria.boot.model.api.response.BaseResponse;
import io.github.dbstarll.algeria.boot.model.api.response.tone.QueryCatalogToneResponse;
import io.github.dbstarll.algeria.boot.model.api.response.user.*;
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
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

public final class RbtApi extends JsonApiClient {
    private final RbtApiSettings settings;
    private final ToneProvide toneProvide;
    private final UserManage userManage;
    private final System system;

    /**
     * 构建RbtApi.
     *
     * @param httpClient HttpClient
     * @param mapper     ObjectMapper
     * @param settings   RbtApiSettings
     */
    public RbtApi(final HttpClient httpClient, final ObjectMapper mapper, final RbtApiSettings settings) {
        super(httpClient, true, optimize(mapper));
        this.settings = settings;
        setUriResolver(new RelativeUriResolver(settings.getUri(), settings.getContext()));
        this.toneProvide = new ToneProvide("/toneprovide");
        this.userManage = new UserManage("/usermanage");
        this.system = new System("/system");
    }

    /**
     * 铃音管理接口.
     *
     * @return ToneProvide
     */
    public ToneProvide tone() {
        return toneProvide;
    }

    /**
     * 用户管理接口.
     *
     * @return UserManage
     */
    public UserManage user() {
        return userManage;
    }

    /**
     * 系统管理接口.
     *
     * @return System
     */
    public System system() {
        return system;
    }

    private static ObjectMapper optimize(final ObjectMapper mapper) {
        return mapper
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    protected <T> T postProcessing(final ClassicHttpRequest request, final T executeResult) throws ApiException {
        final T finalResult = super.postProcessing(request, executeResult);
        final Optional<RbtApiException> optException = Optional.ofNullable(finalResult)
                .filter(ObjectNode.class::isInstance).map(ObjectNode.class::cast)
                .map(node -> node.get("returnCode")).map(JsonNode::textValue)
                .filter(Predicates.negate(BaseResponse.RETURN_CODE_SUCCESS::equals))
                .map(RbtApiException::new);
        if (optException.isPresent()) {
            throw optException.get();
        }
        final Optional<RbtApiException> optBytesException = Optional.ofNullable(finalResult)
                .filter(byte[].class::isInstance).map(byte[].class::cast)
                .filter(d -> d[0] == '{' && d[d.length - 1] == '}')
                .map(d -> new String(d, StandardCharsets.UTF_8))
                .map(s -> {
                    try {
                        return mapper.readTree(s);
                    } catch (JsonProcessingException e) {
                        throw new UnsupportedOperationException(e);
                    }
                })
                .map(node -> node.get("returnCode")).map(JsonNode::textValue)
                .filter(Predicates.negate(BaseResponse.RETURN_CODE_SUCCESS::equals))
                .map(RbtApiException::new);
        if (optBytesException.isPresent()) {
            throw optBytesException.get();
        }
        return finalResult;
    }

    private <T extends BaseRequest> HttpEntity auth(final T request, final Consumer<T> consumer)
            throws JsonProcessingException {
        request.setPortalAccount(settings.getPortalAccount());
        request.setPortalPwd(settings.getPortalPwd());
        request.setPortalType(settings.getPortalType());
        consumer.accept(request);
        return jsonEntity(request);
    }

    private <T extends BaseUpdateRequest> HttpEntity authUpdate(final T request, final Consumer<T> consumer)
            throws JsonProcessingException {
        request.setRole(settings.getRole());
        request.setRoleCode(settings.getRoleCode());
        return auth(request, consumer);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class ToneProvide {
        private final String moduleRoot;

        /**
         * 本接口用于CP操作员在Web Portal上查询指定铃音目录下的以下资源类型：彩铃、音乐盒.
         *
         * @param status Tone state
         * @return QueryCatalogToneResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public QueryCatalogToneResponse query(final String status) throws IOException, ApiException {
            return execute(post(moduleRoot + "/querycatalogtone")
                    .setEntity(auth(new QueryCatalogToneRequest(), request -> {
                        request.setCatalogID(settings.getTone().getCatalogId());
                        request.setResourceType("1");
                        request.setStatus(status);
                    })).build(), QueryCatalogToneResponse.class);
        }

        /**
         * 该接口用于获取试听文件.
         *
         * @param resourceId 铃音ID
         * @return 试听文件字节数组
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public byte[] get(final String resourceId) throws IOException, ApiException {
            return execute(post(moduleRoot + "/getfile").setEntity(auth(new GetFileRequest(),
                    request -> request.setResourceID(resourceId))).build(), byte[].class);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class UserManage {
        private final String moduleRoot;

        private final UserToneManage userToneManage = new UserToneManage("usertonemanage");

        /**
         * 用户订购铃音管理接口.
         *
         * @return UserToneManage
         */
        public UserToneManage tone() {
            return userToneManage;
        }

        /**
         * 本接口用于Portal查询彩铃用户信息。根据返回消息的status字段判断用户状态.
         *
         * @param phone 手机号码
         * @return QueryUserResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public QueryUserResponse queryUser(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/queryuser").setEntity(auth(new QueryUserRequest(),
                    request -> {
                        request.setStatus("2");
                        request.setPhoneNumber(phone);
                    })).build(), QueryUserResponse.class);
        }

        /**
         * 该接口用于查询用户产品.
         *
         * @param phone 手机号码
         * @return QueryUserProductResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public QueryUserProductResponse queryUserProduct(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/queryuserproduct")
                    .setEntity(auth(new QueryUserProductRequest(),
                            request -> {
                                request.setProductID(settings.getUser().getProductId());
                                request.setStatus("2");
                                request.setPhoneNumber(phone);
                            })).build(), QueryUserProductResponse.class);
        }

        /**
         * 本接口用于用户通过Portal订购彩铃业务.
         *
         * @param phone 手机号码
         * @return BaseResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public BaseResponse subscribe(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/subscribe")
                    .setEntity(authUpdate(new SubscribeRequest(),
                            request -> request.setPhoneNumber(phone))).build(), BaseResponse.class);
        }

        /**
         * 该接口用于开通产品.
         *
         * @param phone 手机号码
         * @return SubscribeProductResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public SubscribeProductResponse subscribeProduct(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/subscribeproduct")
                    .setEntity(authUpdate(new SubscribeProductRequest(), request -> {
                        request.setProductID(settings.getUser().getProductId());
                        request.setPhoneNumbers(new String[]{phone});
                    })).build(), SubscribeProductResponse.class);
        }

        /**
         * 该接口用于用户退订产品.
         *
         * @param phone 手机号码
         * @return SubscribeProductResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public SubscribeProductResponse unsubscribeProduct(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/unsubscribeproduct")
                    .setEntity(authUpdate(new UnsubscribeProductRequest(), request -> {
                        request.setProductID(settings.getUser().getProductId());
                        request.setPhoneNumbers(new String[]{phone});
                    })).build(), SubscribeProductResponse.class);
        }

        /**
         * 该接口用于通过下载铃音或音乐盒开通RBT业务特性。在为用户下载RBT或音乐盒之前，系统先为用户开通RBT业务。该接口允许同时对特定用户进行订阅、下载和设置操作.
         *
         * @param phone      手机号码
         * @param resourceId 铃音ID
         * @return EasyDownloadResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public EasyDownloadResponse easyDownload(final String phone, final String resourceId)
                throws IOException, ApiException {
            return execute(post(moduleRoot + "/easydownload")
                    .setEntity(authUpdate(new EasyDownloadRequest(), request -> {
                        request.setPhoneNumber(phone);
                        request.setResourceType("1");
                        request.setResourceID(new String[]{resourceId});
                    })).build(), EasyDownloadResponse.class);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class UserToneManage {
        private final String moduleRoot;

        /**
         * 该接口用于查询用户个人铃音库，用户个人铃音库的availableDateTime在当前日期之前表示铃音欠费.
         *
         * @param phone 手机号码
         * @return QueryInboxToneResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public QueryInboxToneResponse queryInboxTone(final String phone) throws IOException, ApiException {
            return execute(post(moduleRoot + "/queryinboxtone").setEntity(auth(new QueryInboxToneRequest(),
                    request -> {
                        request.setPhoneNumber(phone);
                        request.setResourceType("1");
                        request.setStatus("1");
                    })).build(), QueryInboxToneResponse.class);
        }

        /**
         * 该接口用于用户退订铃音.
         *
         * @param phone      手机号码
         * @param resourceId 铃音ID
         * @return BaseResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public BaseResponse delInboxTone(final String phone, final String resourceId) throws IOException, ApiException {
            return execute(delete(moduleRoot + "/delInboxTone").setEntity(authUpdate(new DelInboxToneRequest(),
                    request -> {
                        request.setPhoneNumber(phone);
                        request.setResourceID(resourceId);
                    })).build(), BaseResponse.class);
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class System {
        private final String moduleRoot;

        /**
         * 发送手机验证码短信.
         *
         * @param phone 手机号码
         * @param code  验证码.
         * @return BaseResponse
         * @throws IOException  in case of a problem or the connection was aborted
         * @throws ApiException in case of an api error
         */
        public BaseResponse sendSm(final String phone, final String code) throws IOException, ApiException {
            return execute(post(moduleRoot + "/sendsm").setEntity(authUpdate(new SendSmRequest(), request -> {
                request.setSmLabel(settings.getSystem().getSmLabel());
                request.setPhoneNumbers(new String[]{phone});
                request.setPlaceHolderParams(new String[]{code});
            })).build(), BaseResponse.class);
        }
    }

    @Getter
    public static final class RbtApiException extends ApiProtocolException {
        private static final long serialVersionUID = 4948918675208623272L;

        private final String returnCode;

        private RbtApiException(final String returnCode) {
            super(String.format("RBT call failed[%s]", returnCode), null);
            this.returnCode = returnCode;
        }
    }
}
