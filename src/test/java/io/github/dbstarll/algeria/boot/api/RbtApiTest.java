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
        assertEquals(30, body.at("/toneInfos/0").size());
        assertEquals("13229395", body.at("/toneInfos/0/toneID").textValue());
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
}