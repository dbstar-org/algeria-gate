package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.AbstractBaseSpringBootTest;
import io.github.dbstarll.algeria.boot.controller.UserController.InspectRequest;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.github.dbstarll.algeria.boot.controller.UserController.ResourceRequest;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest extends AbstractBaseSpringBootTest {
    private static final String TEST_MOBILE = "18210008434";
    private static final String TEST_TONE_ID = "13229395";

    @Test
    void verify() {
        withAccessToken(TEST_MOBILE, token -> {
            final JsonNode verifyBody = getForEntity(token, "/api/user/verify");
            assertEquals(2, verifyBody.size());
            assertEquals(ErrorCodes.SUCCESS, verifyBody.get("code").intValue());
            assertEquals(2, verifyBody.get("data").size());
            assertEquals(TEST_MOBILE, verifyBody.at("/data/phone").textValue());
            assertEquals(1, verifyBody.at("/data/tones").size());
            assertEquals(52, verifyBody.at("/data/tones/0").size());
            assertEquals("13219578", verifyBody.at("/data/tones/0/toneID").textValue());
        });
    }

    @Test
    void verifyFailed() {
        final JsonNode verifyBody = getForEntity(Uuid.toString(Uuid.generate()), "/api/user/verify");
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

            assertEquals(1, body.at("/data").size());
            assertEquals(7, body.at("/data/0").size());
            assertEquals("1", body.at("/data/0/resourceType").textValue());
            assertEquals(TEST_TONE_ID, body.at("/data/0/resourceId").textValue());
            assertEquals("601520000000000016", body.at("/data/0/resourceCode").textValue());
            assertEquals("5000", body.at("/data/0/chargedPrice").textValue());
            assertEquals("60", body.at("/data/0/duration").textValue());
            assertTrue(body.at("/data/0/chargeTime").isTextual());
            assertTrue(body.at("/data/0/relativeExpiryDate").isTextual());
        });

        final JsonNode inspectRbt = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, false), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectRbt.get("code").intValue());
        assertTrue(inspectRbt.get("data").booleanValue());

        final JsonNode inspectVip = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, true), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectVip.get("code").intValue());
        assertFalse(inspectVip.get("data").booleanValue());
    }

    @Test
    void easyDownloadFailed() {
        withAccessToken(TEST_MOBILE, token -> {
            final ResourceRequest request = new ResourceRequest("123456");
            final JsonNode body = postForEntity(token, "/api/user/easy-download", request);
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
            assertEquals("1#13229395", body.at("/data").textValue());
        });

        final JsonNode inspectRbt = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, false), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectRbt.get("code").intValue());
        assertFalse(inspectRbt.get("data").booleanValue());

        final JsonNode inspectVip = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, true), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectVip.get("code").intValue());
        assertFalse(inspectVip.get("data").booleanValue());
    }

    @Test
    void inspect() {
        final JsonNode inspectRbt = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, false), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectRbt.get("code").intValue());
        assertFalse(inspectRbt.get("data").booleanValue());

        final JsonNode inspectVip = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, true), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectVip.get("code").intValue());
        assertFalse(inspectVip.get("data").booleanValue());
    }

    @Order(10)
    @Test
    void subscribeVip() {
        withAccessToken(TEST_MOBILE, token -> {
            final JsonNode body = getForEntity(token, "/api/user/subscribe-vip");
            assertEquals(2, body.size());
            assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
            assertEquals(1, body.get("data").size());
            assertEquals(4, body.at("/data/0").size());
            assertEquals(TEST_MOBILE, body.at("/data/0/phoneNumber").textValue());
            assertEquals("000000", body.at("/data/0/resultCode").textValue());
            assertEquals("2", body.at("/data/0/status").textValue());
            assertEquals("", body.at("/data/0/serviceID").textValue());
        });

        final JsonNode inspectRbt = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, false), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectRbt.get("code").intValue());
        assertTrue(inspectRbt.get("data").booleanValue());

        final JsonNode inspectVip = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, true), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectVip.get("code").intValue());
        assertTrue(inspectVip.get("data").booleanValue());
    }

    @Order(20)
    @Test
    void unsubscribeVip() {
        withAccessToken(TEST_MOBILE, token -> {
            final JsonNode body = getForEntity(token, "/api/user/unsubscribe-vip");
            System.out.println(body);
            assertEquals(2, body.size());
            assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
            assertEquals(1, body.get("data").size());
            assertEquals(4, body.at("/data/0").size());
            assertEquals(TEST_MOBILE, body.at("/data/0/phoneNumber").textValue());
            assertEquals("000000", body.at("/data/0/resultCode").textValue());
            assertEquals("4", body.at("/data/0/status").textValue());
            assertEquals("", body.at("/data/0/serviceID").textValue());
        });

        final JsonNode inspectRbt = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, false), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectRbt.get("code").intValue());
        assertFalse(inspectRbt.get("data").booleanValue());

        final JsonNode inspectVip = restTemplate.postForObject("/api/user/inspect", new InspectRequest(TEST_MOBILE, true), JsonNode.class);
        assertEquals(ErrorCodes.SUCCESS, inspectVip.get("code").intValue());
        assertFalse(inspectVip.get("data").booleanValue());
    }
}