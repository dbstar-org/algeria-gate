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
        final JsonNode body = objectMapper.valueToTree(rbtApi.queryCatalogTone("1"));
        assertNotNull(body);
        assertEquals(5, body.size());
        assertEquals("000000", body.get("returnCode").textValue());
        assertEquals("-1", body.get("recordSum").textValue());
        assertEquals("0", body.get("operationID").textValue());
        assertEquals("0", body.get("resultCode").textValue());
        assertEquals(3, body.get("toneInfos").size());
        assertEquals(30, body.at("/toneInfos/0").size());
        assertEquals("13239642", body.at("/toneInfos/0/toneID").textValue());
        assertEquals(30, body.at("/toneInfos/1").size());
        assertEquals("13239639", body.at("/toneInfos/1/toneID").textValue());
        assertEquals(30, body.at("/toneInfos/2").size());
        assertEquals("13239641", body.at("/toneInfos/2/toneID").textValue());
    }

    @Test
    void queryCatalogToneFailed() {
        final RbtApiException e = assertThrowsExactly(RbtApiException.class, () -> rbtApi.queryCatalogTone("5"));
        assertEquals("RBT call failed[200002]", e.getMessage());
        assertEquals("200002", e.getReturnCode());
        assertNull(e.getCause());
    }
}