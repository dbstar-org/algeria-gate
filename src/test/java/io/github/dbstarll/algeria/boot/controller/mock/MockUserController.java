package io.github.dbstarll.algeria.boot.controller.mock;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dbstarll.algeria.boot.model.api.request.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping(path = "/usermanage", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class MockUserController extends BaseMockController {
    @PostMapping("/queryuser")
    JsonNode queryUser(@Valid @RequestBody final QueryUserRequest request) throws IOException {
        log.debug("queryUser: {}", request);
        return json("/response/queryuser.json");
    }

    @PostMapping("/queryuserproduct")
    JsonNode queryUserProduct(@Valid @RequestBody final QueryUserProductRequest request) throws IOException {
        log.debug("queryUserProduct: {}", request);
        return json("/response/queryuserproduct-" + product.get() + ".json");
    }

    @PostMapping("/easydownload")
    JsonNode easyDownload(@Valid @RequestBody final EasyDownloadRequest request) throws IOException {
        log.debug("easyDownload: {}", request);
        if (TEST_MOBILE.equals(request.getPhoneNumber())) {
            subscribe.set(true);
            if (Arrays.asList(request.getResourceID()).contains(TEST_TONE_ID)) {
                tone.set(true);
            } else {
                return json("/response/easydownload-failed.json");
            }
        }
        return json("/response/easydownload.json");
    }

    @PostMapping("/subscribeproduct")
    JsonNode subscribeProduct(@Valid @RequestBody final SubscribeProductRequest request) throws IOException {
        log.debug("subscribeProduct: {}", request);
        if (Arrays.asList(request.getPhoneNumbers()).contains(TEST_MOBILE)) {
            product.set(true);
        }
        return json("/response/subscribeproduct.json");
    }

    @PostMapping("/unsubscribeproduct")
    JsonNode unsubscribeProduct(@Valid @RequestBody final UnsubscribeProductRequest request) throws IOException {
        log.debug("unsubscribeProduct: {}", request);
        if (Arrays.asList(request.getPhoneNumbers()).contains(TEST_MOBILE)) {
            product.set(false);
        }
        return json("/response/unsubscribeproduct.json");
    }

    @PostMapping("/subscribe")
    JsonNode subscribe(@Valid @RequestBody final SubscribeRequest request) throws IOException {
        log.debug("subscribe: {}", request);
        if (TEST_MOBILE.equals(request.getPhoneNumber())) {
            if (subscribe.get()) {
                return json("/response/subscribe-failed.json");
            }
            subscribe.set(true);
        }
        return post("/usermanage/subscribe", request);
    }
}
