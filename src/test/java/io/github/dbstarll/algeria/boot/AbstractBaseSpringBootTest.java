package io.github.dbstarll.algeria.boot;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractBaseSpringBootTest {
    protected static final String JAVA_VERSION = System.getProperty("java.version");
    protected static final String LOCAL_AGENT = "Java/" + JAVA_VERSION;

    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeEach
    protected void setUpRestTemplate() {
        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zh");
            return execution.execute(request, body);
        }));
        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    protected final void withAccessToken(final String phone, final Consumer<String> consumer) {
        final Map<String, String> request = Collections.singletonMap("phone", phone);
        final ResponseEntity<JsonNode> res = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final JsonNode body = res.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertTrue(body.get("data").booleanValue());

        final Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("phone", phone);
        loginRequest.put("verifyCode", "476287");
        final ResponseEntity<JsonNode> loginRes = restTemplate.postForEntity("/api/login/phone", loginRequest, JsonNode.class);
        assertEquals(HttpStatus.OK, loginRes.getStatusCode());
        final JsonNode loginBody = loginRes.getBody();
        assertNotNull(loginBody);
        assertEquals(2, loginBody.size());
        assertEquals(ErrorCodes.SUCCESS, loginBody.get("code").intValue());
        assertTrue(loginBody.get("data").isTextual());

        consumer.accept(loginBody.get("data").textValue());
    }

    protected final <R> JsonNode postForEntity(final String accessToken, final String url, final R request) {
        return postForEntity(accessToken, url, request, JsonNode.class);
    }

    protected final <R, T> T postForEntity(final String accessToken, final String url, final R request,
                                           final Class<T> responseType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("gate-access-token", accessToken);
        final ResponseEntity<T> res = restTemplate.postForEntity(url, new HttpEntity<>(request, headers), responseType);
        assertEquals(200, res.getStatusCodeValue());
        final T body = res.getBody();
        assertNotNull(body);
        return body;
    }

    protected final JsonNode getForEntity(final String accessToken, final String url) {
        return getForEntity(accessToken, url, JsonNode.class);
    }

    protected final JsonNode getForEntity(final String accessToken, final String url, final HttpStatus status) {
        return getForEntity(accessToken, url, JsonNode.class, status);
    }

    protected final <T> T getForEntity(final String accessToken, final String url, final Class<T> responseType) {
        return getForEntity(accessToken, url, responseType, HttpStatus.OK);
    }

    protected final <T> T getForEntity(final String accessToken, final String url, final Class<T> responseType, final HttpStatus status) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("gate-access-token", accessToken);
        final ResponseEntity<T> res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
        assertEquals(status, res.getStatusCode());
        final T body = res.getBody();
        assertNotNull(body);
        return body;
    }
}
