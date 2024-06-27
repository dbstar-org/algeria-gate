package io.github.dbstarll.algeria.boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.AbstractBaseSpringBootTest;
import io.github.dbstarll.algeria.boot.error.ErrorCodes;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserControllerTest extends AbstractBaseSpringBootTest {
    @Test
    void verify() {
        final JsonNode verifyBody = getForEntity(Uuid.toString(Uuid.generate()), "/api/user/verify", HttpStatus.UNAUTHORIZED);
        assertNotNull(verifyBody);
        assertEquals(3, verifyBody.size());
        assertEquals(ErrorCodes.INVALID_ACCESS_TOKEN, verifyBody.get("code").intValue());
        assertEquals("Invalid Access-Token", verifyBody.get("message").textValue());
        assertEquals("InvalidAccessTokenException", verifyBody.get("type").textValue());
    }
}