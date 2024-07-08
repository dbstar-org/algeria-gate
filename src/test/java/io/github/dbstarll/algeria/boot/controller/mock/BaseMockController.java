package io.github.dbstarll.algeria.boot.controller.mock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

abstract class BaseMockController {
    @Autowired
    private ObjectMapper objectMapper;

    protected final JsonNode json(String path) throws IOException {
        return objectMapper.readTree(IOUtils.resourceToString(path, StandardCharsets.UTF_8));
    }
}
