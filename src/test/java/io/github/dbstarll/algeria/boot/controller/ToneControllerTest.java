package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static io.github.dbstarll.algeria.boot.controller.ToneController.TonePageableRequest;
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
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertEquals(1, body.get("data").intValue());
    }

    @Test
    void list() {
        final JsonNode body = restTemplate.postForObject("/api/tone/list", new TonePageableRequest(), JsonNode.class);
        assertNotNull(body);
        assertEquals(2, body.size());
        assertEquals(ErrorCodes.SUCCESS, body.get("code").intValue());
        assertEquals(5, body.at("/data").size());
        assertEquals(1, body.at("/data/numberOfElements").intValue());
        assertEquals(1, body.at("/data/totalPages").intValue());
        assertEquals(1, body.at("/data/totalElements").intValue());
        assertEquals(3, body.at("/data/pageable").size());
        assertEquals(0, body.at("/data/pageable/pageNumber").intValue());
        assertEquals(10, body.at("/data/pageable/pageSize").intValue());
        assertEquals(1, body.at("/data/pageable/sort").size());
        assertEquals(2, body.at("/data/pageable/sort/0").size());
        assertEquals("createTime", body.at("/data/pageable/sort/0/property").textValue());
        assertEquals("DESC", body.at("/data/pageable/sort/0/direction").textValue());
        assertEquals(1, body.at("/data/content").size());
        assertEquals(35, body.at("/data/content/0").size());
        assertEquals("13229395", body.at("/data/content/0/toneID").textValue());
        assertEquals("520016", body.at("/data/content/0/toneCode").textValue());
    }
}