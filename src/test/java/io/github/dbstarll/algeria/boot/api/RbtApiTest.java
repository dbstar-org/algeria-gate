package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dbstarll.algeria.boot.api.RbtApi.RbtApiException;
import io.github.dbstarll.utils.net.api.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class RbtApiTest {
    private static final String TEST_MOBILE = "18210008434";

    @Autowired
    private RbtApi rbtApi;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void queryCatalogTone() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.tone().query("1"));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("toneInfos").size());
        assertEquals(35, body.at("/toneInfos/0").size());
        assertEquals("13229395", body.at("/toneInfos/0/toneID").textValue());
        assertTrue(body.at("/toneInfos/0/singerInfos").isMissingNode());
    }

    @Test
    void queryCatalogToneFailed() {
        final RbtApiException e = assertThrowsExactly(RbtApiException.class, () -> rbtApi.tone().query("5"));
        assertEquals("RBT call failed[200002]", e.getMessage());
        assertEquals("200002", e.getReturnCode());
        assertNull(e.getCause());
    }

    @Test
    void getFile() throws IOException, ApiException {
        final byte[] data = rbtApi.tone().get("13229395");
        assertNotNull(data);
        assertEquals(7737, data.length);
    }

    @Test
    void getFileNotExist() {
        final RbtApiException e = assertThrowsExactly(RbtApiException.class, () -> rbtApi.tone().get("13229000"));
        assertEquals("RBT call failed[302003]", e.getMessage());
        assertEquals("302003", e.getReturnCode());
        assertNull(e.getCause());
    }

    @Test
    void queryUser() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().queryUser(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("userInfos").size());
        assertEquals(29, body.at("/userInfos/0").size());
        assertEquals(TEST_MOBILE, body.at("/userInfos/0/phoneNumber").textValue());
        assertEquals(1, body.at("/userInfos/0/serviceOrders").size());
        assertEquals(18, body.at("/userInfos/0/serviceOrders/0").size());
        assertEquals("44070863", body.at("/userInfos/0/serviceOrders/0/orderID").textValue());
    }

    @Test
    void queryInboxTone() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().tone().queryInboxTone(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("toneInfos").size());
        assertEquals(52, body.at("/toneInfos/0").size());
        assertEquals("13219578", body.at("/toneInfos/0/toneID").textValue());
        assertTrue(body.at("/toneInfos/0/singerInfos").isMissingNode());
    }

    @Test
    void queryUserProduct() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().queryUserProduct(TEST_MOBILE));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("toneInfos").size());
        assertEquals(52, body.at("/toneInfos/0").size());
        assertEquals("13219578", body.at("/toneInfos/0/toneID").textValue());
        assertTrue(body.at("/toneInfos/0/singerInfos").isMissingNode());
    }

    @Test
    void subscribe() {
        final RbtApiException e = assertThrowsExactly(RbtApiException.class, () -> rbtApi.user().subscribe(TEST_MOBILE));
        assertEquals("RBT call failed[301009]", e.getMessage());
        assertEquals("301009", e.getReturnCode());
        assertNull(e.getCause());
    }

    @Test
    void sendSm() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.system().sendSm(TEST_MOBILE, "478722"));
        assertNotNull(body);
        assertEquals(3, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
    }
}