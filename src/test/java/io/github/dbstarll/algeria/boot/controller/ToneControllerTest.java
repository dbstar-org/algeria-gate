package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ToneControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void update() {
        final JsonNode body = restTemplate.getForObject("/api/tone/update", JsonNode.class);
        assertNotNull(body);
        assertEquals(1, body.intValue());
    }

    @Test
    void list() {
        final JsonNode body = restTemplate.getForObject("/api/tone/list", JsonNode.class);
        assertNotNull(body);
        assertEquals(1, body.size());
        assertEquals(35, body.at("/0").size());
        assertEquals("13229395", body.at("/0/toneID").textValue());
        assertEquals("520016", body.at("/0/toneCode").textValue());
    }
}