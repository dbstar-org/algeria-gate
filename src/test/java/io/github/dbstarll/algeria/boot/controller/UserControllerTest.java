package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.AbstractBaseSpringBootTest;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.github.dbstarll.algeria.boot.controller.UserController.ResourceRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest extends AbstractBaseSpringBootTest {
    private static final String TEST_MOBILE = "18210008435";
    private static final String TEST_TONE_ID = "13229395";

    @Test
    void verify() {
        withAccessToken(TEST_MOBILE, token -> {
            final JsonNode verifyBody = getForEntity(token, "/api/user/verify");
            assertEquals(2, verifyBody.size());
            assertEquals(ErrorCodes.SUCCESS, verifyBody.get("code").intValue());
            assertEquals(4, verifyBody.get("data").size());
            assertTrue(verifyBody.at("/data/time").isLong());
            assertEquals(TEST_MOBILE, verifyBody.at("/data/phone").textValue());
            assertEquals(1, verifyBody.at("/data/users").size());
            assertEquals(26, verifyBody.at("/data/users/0").size());
            assertEquals(0, verifyBody.at("/data/tones").size());
        });
    }

    @Test
    void verifyFailed() {
        final JsonNode verifyBody = getForEntity(Uuid.toString(Uuid.generate()), "/api/user/verify", HttpStatus.UNAUTHORIZED);
        assertEquals(3, verifyBody.size());
        assertEquals(ErrorCodes.INVALID_ACCESS_TOKEN, verifyBody.get("code").intValue());
        assertEquals("Invalid Access-Token", verifyBody.get("message").textValue());
        assertEquals("InvalidAccessTokenException", verifyBody.get("type").textValue());
    }

    @Test
    void easyDownload() {
        withAccessToken(TEST_MOBILE, token -> {
            final ResourceRequest request = new ResourceRequest(TEST_TONE_ID);
            final JsonNode body = postForEntity(token, "/api/user/easy-download", request);
            assertEquals(2, body.size());
            assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
            assertEquals(1, body.get("data").size());
            assertEquals(7, body.at("/data/0").size());
            assertEquals("1", body.at("/data/0/resourceType").textValue());
            assertEquals(TEST_TONE_ID, body.at("/data/0/resourceId").textValue());
            assertEquals("601520000000000016", body.at("/data/0/resourceCode").textValue());
            assertEquals("5000", body.at("/data/0/chargedPrice").textValue());
            assertEquals("60", body.at("/data/0/duration").textValue());
            assertTrue(body.at("/data/0/chargeTime").isTextual());
            assertTrue(body.at("/data/0/relativeExpiryDate").isTextual());
        });
    }

    @Test
    void easyDownloadFailed() {
        withAccessToken(TEST_MOBILE, token -> {
            final ResourceRequest request = new ResourceRequest("123456");
            final JsonNode body = postForEntity(token, "/api/user/easy-download", request, HttpStatus.INTERNAL_SERVER_ERROR);
            assertEquals(4, body.size());
            assertEquals(ErrorCodes.RBT_API_FAILED, body.get("code").intValue());
            assertEquals("RBT call failed[301549]", body.get("message").textValue());
            assertEquals("RbtApiException", body.get("type").textValue());
            assertEquals(1, body.get("data").size());
            assertEquals("301549", body.at("/data/returnCode").textValue());
        });
    }

    @Test
    void delInboxTone() {
        withAccessToken(TEST_MOBILE, token -> {
            final ResourceRequest request = new ResourceRequest(TEST_TONE_ID);
            final JsonNode body = postForEntity(token, "/api/user/del-inbox-tone", request);
            assertEquals(2, body.size());
            assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
            assertEquals("1#13229395", body.get("data").textValue());
        });
    }
}