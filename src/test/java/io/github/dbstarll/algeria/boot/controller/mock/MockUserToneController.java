package io.github.dbstarll.algeria.boot.controller.mock;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.model.api.request.user.DelInboxToneRequest;
import io.github.dbstarll.algeria.boot.model.api.request.user.QueryInboxToneRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(path = "/usertonemanage", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class MockUserToneController extends BaseMockController {
    @PostMapping("/queryinboxtone")
    JsonNode queryInboxTone(@Valid @RequestBody final QueryInboxToneRequest request) throws IOException {
        log.debug("queryInboxTone: {}", request);
        return post("/usertonemanage/queryinboxtone", request);
//        return json("/response/queryuser.json");
    }

    @DeleteMapping("/delInboxTone")
    JsonNode delInboxTone(@Valid @RequestBody final DelInboxToneRequest request) throws IOException {
        log.debug("delInboxTone: {}", request);
        if (TEST_MOBILE.equals(request.getPhoneNumber()) && TEST_TONE_ID.equals(request.getResourceID())) {
            tone.set(false);
        }
        return delete("/usertonemanage/delInboxTone", request);
//        return json("/response/delInboxTone.json");
    }
}
