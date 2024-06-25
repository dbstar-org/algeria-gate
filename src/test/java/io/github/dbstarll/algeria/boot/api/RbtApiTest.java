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
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class RbtApiTest {
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
    void getFileNotExist() throws IOException, ApiException {
        final byte[] data = rbtApi.tone().get("13229000");
        assertNotNull(data);
        System.out.println(new String(data, StandardCharsets.UTF_8));
        assertEquals(115, data.length);
    }

    @Test
    void queryUser() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().queryUser("18210008434"));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(1, body.get("userInfos").size());
        assertEquals(29, body.at("/userInfos/0").size());
        assertEquals("18210008434", body.at("/userInfos/0/phoneNumber").textValue());
        assertEquals(1, body.at("/userInfos/0/serviceOrders").size());
        assertEquals(18, body.at("/userInfos/0/serviceOrders/0").size());
        assertEquals("44070863", body.at("/userInfos/0/serviceOrders/0/orderID").textValue());
    }

    @Test
    void queryInboxTone() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().tone().queryInboxTone("18210008434"));
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
        final JsonNode body = objectMapper.valueToTree(rbtApi.user().queryUserProduct("18176402111"));
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
    void sendSm() throws IOException, ApiException {
        final JsonNode body = objectMapper.valueToTree(rbtApi.system().sendSm("18918606202", "478722"));
        assertNotNull(body);
        assertEquals(3, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
    }
}