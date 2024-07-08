package io.github.dbstarll.algeria.boot.controller.mock;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.model.api.request.system.SendSmRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/system", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class MockSystemController extends BaseMockController {
    @PostMapping("/sendsm")
    JsonNode sendSm(@Valid @RequestBody final SendSmRequest request) throws IOException {
        log.debug("sendSm: {}", request);
        return json("/response/sendsm.json");
    }
}
