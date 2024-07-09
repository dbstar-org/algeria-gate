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
        return post("/usermanage/queryuserproduct", request);
//        return json("/response/queryuserproduct.json");
    }

    @PostMapping("/easydownload")
    JsonNode easyDownload(@Valid @RequestBody final EasyDownloadRequest request) throws IOException {
        log.debug("easyDownload: {}", request);
        if (TEST_MOBILE.equals(request.getPhoneNumber())) {
            subscribe.set(true);
            if (Arrays.asList(request.getResourceID()).contains(TEST_TONE_ID)) {
                tone.set(true);
            }
        }
        return post("/usermanage/easydownload", request);
//        return json("/response/queryuserproduct.json");
    }

    @PostMapping("/subscribeproduct")
    JsonNode subscribeProduct(@Valid @RequestBody final SubscribeProductRequest request) throws IOException {
        log.debug("subscribeProduct: {}", request);
        if (Arrays.asList(request.getPhoneNumbers()).contains(TEST_MOBILE)) {
            product.set(true);
        }
        return post("/usermanage/subscribeproduct", request);
//        return json("/response/queryuserproduct.json");
    }

    @PostMapping("/unsubscribeproduct")
    JsonNode unsubscribeProduct(@Valid @RequestBody final UnsubscribeProductRequest request) throws IOException {
        log.debug("unsubscribeProduct: {}", request);
        if (Arrays.asList(request.getPhoneNumbers()).contains(TEST_MOBILE)) {
            product.set(false);
        }
        return post("/usermanage/unsubscribeproduct", request);
//        return json("/response/queryuserproduct.json");
    }

    @PostMapping("/subscribe")
    JsonNode subscribe(@Valid @RequestBody final SubscribeRequest request) throws IOException {
        log.debug("subscribe: {}", request);
        if (TEST_MOBILE.equals(request.getPhoneNumber())) {
            subscribe.set(true);
        }
        return post("/usermanage/subscribe", request);
//        return json("/response/queryuserproduct.json");
    }
}
