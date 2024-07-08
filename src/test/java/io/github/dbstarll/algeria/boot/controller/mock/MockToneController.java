package io.github.dbstarll.algeria.boot.controller.mock;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.model.api.request.tone.QueryCatalogToneRequest;
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
@RequestMapping(path = "/toneprovide", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class MockToneController extends BaseMockController {
    @PostMapping("/querycatalogtone")
    JsonNode queryCatalogTone(@Valid @RequestBody final QueryCatalogToneRequest request) throws IOException {
        log.debug("queryCatalogTone: {}", request);
        return json("/response/querycatalogtone.json");
    }
}
