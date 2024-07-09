package io.github.dbstarll.algeria.boot.controller.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.utils.http.client.response.ByteArrayResponseHandler;
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

abstract class BaseMockController {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private HttpClient httpClient;

    protected final JsonNode json(String path) throws IOException {
        return mapper.readTree(IOUtils.resourceToString(path, StandardCharsets.UTF_8));
    }

    protected final <T> JsonNode post(final String url, final T request) throws IOException {
        final String json = httpClient.execute(ClassicRequestBuilder
                .post("https://116.63.194.38:17132/apiaccess/gw/rest" + url)
                .setEntity(jsonEntity(request))
                .build(), new BasicHttpClientResponseHandler());
        System.out.println(json);
        return mapper.readTree(json);
    }

    protected final <T> byte[] postBytes(final String url, final T request) throws IOException {
        return httpClient.execute(ClassicRequestBuilder
                .post("https://116.63.194.38:17132/apiaccess/gw/rest" + url)
                .setEntity(jsonEntity(request))
                .build(), new ByteArrayResponseHandler(true));
    }

    protected final <T> JsonNode delete(final String url, final T request) throws IOException {
        final String json = httpClient.execute(ClassicRequestBuilder
                .delete("https://116.63.194.38:17132/apiaccess/gw/rest" + url)
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
