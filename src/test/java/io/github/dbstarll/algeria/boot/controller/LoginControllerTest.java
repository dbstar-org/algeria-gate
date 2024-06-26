package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.github.dbstarll.algeria.boot.controller.LoginController.*;
import static io.github.dbstarll.algeria.boot.controller.LoginController.PhoneRequest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoginControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void verifyCode() {
        final PhoneRequest request = new PhoneRequest("123456789");
        final ResponseEntity<JsonNode> res = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final JsonNode body = res.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertTrue(body.get("data").booleanValue());

        final ResponseEntity<JsonNode> res2 = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res2.getStatusCode());
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
    void loginPhone() {
        final PhoneRequest request = new PhoneRequest("1234567890");
        final ResponseEntity<JsonNode> res = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final JsonNode body = res.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertTrue(body.get("data").booleanValue());

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("1234567890");
        loginRequest.setVerifyCode("000000");
        final ResponseEntity<JsonNode> loginRes = restTemplate.postForEntity("/api/login/phone", loginRequest, JsonNode.class);
        assertEquals(HttpStatus.OK, loginRes.getStatusCode());
        final JsonNode loginBody = loginRes.getBody();
        assertNotNull(loginBody);
        assertEquals(2, loginBody.size());
        assertEquals(ErrorCodes.SUCCESS, loginBody.get("code").intValue());
        assertTrue(loginBody.get("data").isTextual());
    }
}