package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static io.github.dbstarll.algeria.boot.controller.LoginController.LoginRequest;
import static io.github.dbstarll.algeria.boot.controller.LoginController.PhoneRequest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoginControllerTest {
    private static final String TEST_MOBILE = "18210008434";
    private static final String TEST_TONE_ID = "13229395";

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
        final PhoneRequest request = new PhoneRequest(TEST_MOBILE);
        final ResponseEntity<JsonNode> res = restTemplate.postForEntity("/api/login/verify-code", request, JsonNode.class);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        final JsonNode body = res.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertTrue(body.get("data").booleanValue());

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone(TEST_MOBILE);
        loginRequest.setVerifyCode("000000");
        final ResponseEntity<JsonNode> loginRes = restTemplate.postForEntity("/api/login/phone", loginRequest, JsonNode.class);
        assertEquals(HttpStatus.OK, loginRes.getStatusCode());
        final JsonNode loginBody = loginRes.getBody();
        assertNotNull(loginBody);
        assertEquals(2, loginBody.size());
        assertEquals(ErrorCodes.SUCCESS, loginBody.get("code").intValue());
        assertTrue(loginBody.get("data").isTextual());

        final HttpHeaders headers = new HttpHeaders();
        headers.set("gate-access-token", loginBody.get("data").textValue());
        final ResponseEntity<JsonNode> verifyRes = restTemplate.exchange("/api/login/verify", HttpMethod.GET, new HttpEntity<>(headers), JsonNode.class);
        assertEquals(HttpStatus.OK, verifyRes.getStatusCode());
        final JsonNode verifyBody = verifyRes.getBody();
        assertNotNull(verifyBody);
        assertEquals(2, verifyBody.size());
        assertEquals(ErrorCodes.SUCCESS, verifyBody.get("code").intValue());
        assertEquals(5, verifyBody.get("data").size());
        assertTrue(verifyBody.at("/data/time").isLong());
        assertEquals(TEST_MOBILE, verifyBody.at("/data/phone").textValue());
        assertEquals(1, verifyBody.at("/data/users").size());
        assertEquals(29, verifyBody.at("/data/users/0").size());
        assertEquals(1, verifyBody.at("/data/products").size());
        assertEquals(2, verifyBody.at("/data/products/0").size());
        assertEquals(1, verifyBody.at("/data/tones").size());
        assertEquals(52, verifyBody.at("/data/tones/0").size());
    }

    @Test
    void verify() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("gate-access-token", Uuid.toString(Uuid.generate()));
        final ResponseEntity<JsonNode> verifyRes = restTemplate.exchange("/api/login/verify", HttpMethod.GET, new HttpEntity<>(headers), JsonNode.class);
        assertEquals(HttpStatus.UNAUTHORIZED, verifyRes.getStatusCode());
        final JsonNode verifyBody = verifyRes.getBody();
        assertNotNull(verifyBody);
        assertEquals(3, verifyBody.size());
        assertEquals(ErrorCodes.INVALID_ACCESS_TOKEN, verifyBody.get("code").intValue());
        assertEquals("Invalid Access-Token", verifyBody.get("message").textValue());
        assertEquals("InvalidAccessTokenException", verifyBody.get("type").textValue());
    }
}