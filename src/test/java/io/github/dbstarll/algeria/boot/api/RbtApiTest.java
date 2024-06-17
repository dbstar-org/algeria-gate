package io.github.dbstarll.algeria.boot.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.utils.net.api.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class RbtApiTest {
    @Autowired
    private RbtApi rbtApi;

    @Test
    void queryCatalogTone() throws IOException, ApiException {
        final JsonNode body = rbtApi.queryCatalogTone("1");
        assertNotNull(body);
        System.out.println(body);
    }

    @Test
    void queryCatalogToneFailed() throws IOException, ApiException {
        final JsonNode body = rbtApi.queryCatalogTone("5");
        assertNotNull(body);
        System.out.println(body);
    }
}