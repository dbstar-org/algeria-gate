package io.github.dbstarll.algeria.boot;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import io.github.dbstarll.algeria.boot.mdc.AccessTokenHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractBaseSpringBootTest {
    protected static final String JAVA_VERSION = System.getProperty("java.version");
    protected static final String LOCAL_AGENT = "Java/" + JAVA_VERSION;

    @Autowired
    protected TestRestTemplate restTemplate;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

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

    @AfterEach
    void cleanDatabase() throws SQLException {
        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            try (ResultSet tables = connection.getMetaData().getTables(null, "PUBLIC", null, new String[]{"TABLE"})) {
                while (tables.next()) {
                    final String tableName = tables.getString("TABLE_NAME");
                    if (tableName.startsWith("AG_")) {
                        jdbcTemplate.update("DELETE FROM " + tableName);
                    }
                }
            }
        }
    }

    @AfterEach
    void cleanRedis() {
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushAll();
            return null;
        });
    }

    protected final void withAccessToken(final String phone, final Consumer<String> consumer) {
        final Map<String, String> request = Collections.singletonMap("phone", phone);
        final ResponseEntity<JsonNode> res = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final JsonNode body = res.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertEquals(1, body.get("data").size());
        assertEquals(60, body.at("/data/wait").intValue());

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

    protected final <R, T> T postForEntity(final String accessToken, final String url, final R request, final Class<T> responseType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(AccessTokenHolder.HEADER_ACCESS_TOKEN, accessToken);
        final ResponseEntity<T> res = restTemplate.postForEntity(url, new HttpEntity<>(request, headers), responseType);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final T body = res.getBody();
        assertNotNull(body);
        return body;
    }

    protected final JsonNode getForEntity(final String accessToken, final String url) {
        return getForEntity(accessToken, url, JsonNode.class);
    }

    protected final <T> T getForEntity(final String accessToken, final String url, final Class<T> responseType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(AccessTokenHolder.HEADER_ACCESS_TOKEN, accessToken);
        final ResponseEntity<T> res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final T body = res.getBody();
        assertNotNull(body);
        return body;
    }
}
