package io.github.dbstarll.algeria.boot.controller.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseMockController {
    public static final AtomicBoolean subscribe = new AtomicBoolean(false);
    public static final AtomicBoolean product = new AtomicBoolean(false);
    public static final AtomicBoolean tone = new AtomicBoolean(false);

    protected static final String TEST_MOBILE = "18210008434";
    protected static final String TEST_TONE_ID = "13229395";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private HttpClient httpClient;

    protected final JsonNode json(String path) throws IOException {
        return mapper.readTree(IOUtils.resourceToString(path, StandardCharsets.UTF_8));
    }

    protected final byte[] bytes(String path) throws IOException {
        return Base64.getDecoder().decode(IOUtils.resourceToString(path, StandardCharsets.UTF_8));
    }

    protected final <T> JsonNode post(final String url, final T request) throws IOException {
        final String json = httpClient.execute(ClassicRequestBuilder
                .post("https://116.63.194.38:17132/apiaccess/gw/rest" + url)
                .setEntity(jsonEntity(request))
                .build(), new BasicHttpClientResponseHandler());
        System.out.println(json);
        return mapper.readTree(json);
    }

    private <T> HttpEntity jsonEntity(T request) throws JsonProcessingException {
        return EntityBuilder.create()
                .setText(mapper.writeValueAsString(request))
                .setContentType(ContentType.APPLICATION_JSON)
                .setContentEncoding("UTF-8")
                .build();
    }
}
