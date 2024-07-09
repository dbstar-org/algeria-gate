package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.AbstractBaseSpringBootTest;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.github.dbstarll.algeria.boot.controller.LoginController.LoginRequest;
import static io.github.dbstarll.algeria.boot.controller.LoginController.PhoneRequest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoginControllerTest extends AbstractBaseSpringBootTest {
    private static final String TEST_MOBILE = "18210008434";

    @Test
    void verifyCode() {
        final PhoneRequest request = new PhoneRequest("123456789");
        final ResponseEntity<JsonNode> res = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final JsonNode body = res.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertEquals(1, body.get("data").size());
        assertEquals(60, body.at("/data/wait").intValue());

        final ResponseEntity<JsonNode> res2 = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res2.getStatusCode());
        final JsonNode body2 = res2.getBody();
        assertNotNull(body2);
        assertEquals(4, body2.size());
        assertEquals(ErrorCodes.FREQUENTLY_OBTAIN_VERIFY_CODE, body2.get("code").intValue());
        assertEquals("获取验证码过于频繁，请稍后再试！", body2.get("message").textValue());
        assertEquals("FrequentlyObtainVerifyCodeException", body2.get("type").textValue());
        assertEquals(1, body2.get("data").size());
        assertEquals("123456789", body2.at("/data/phone").textValue());
    }

    @Test
    void login() {
        final PhoneRequest request = new PhoneRequest(TEST_MOBILE);
        final ResponseEntity<JsonNode> res = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final JsonNode body = res.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertEquals(1, body.get("data").size());
        assertEquals(60, body.at("/data/wait").intValue());

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone(TEST_MOBILE);
        loginRequest.setVerifyCode("476287");
        final ResponseEntity<JsonNode> loginRes = restTemplate.postForEntity("/api/login/phone", loginRequest, JsonNode.class);
        assertEquals(HttpStatus.OK, loginRes.getStatusCode());
        final JsonNode loginBody = loginRes.getBody();
        assertNotNull(loginBody);
        assertEquals(2, loginBody.size());
        assertEquals(ErrorCodes.SUCCESS, loginBody.get("code").intValue());
        assertTrue(loginBody.get("data").isTextual());
    }

    @Test
    void logout() {
        withAccessToken(TEST_MOBILE, token -> {
            final JsonNode body = getForEntity(token, "/api/login/logout");
            assertEquals(2, body.size());
            assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
            assertEquals(3, body.get("data").size());
            assertEquals(TEST_MOBILE, body.at("/data/phone").textValue());
            assertEquals(1, body.at("/data/users").size());
            assertEquals(26, body.at("/data/users/0").size());
            assertEquals(1, body.at("/data/tones").size());
            assertEquals(52, body.at("/data/tones/0").size());
        });
    }
}