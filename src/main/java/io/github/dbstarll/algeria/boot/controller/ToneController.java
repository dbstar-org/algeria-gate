package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.algeria.boot.model.response.BasePageableRequest;
import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import io.github.dbstarll.algeria.boot.model.response.PageableResponse;
import io.github.dbstarll.algeria.boot.service.ToneService;
import io.github.dbstarll.utils.net.api.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "彩铃管理")
@RestController
@RequestMapping(path = "/api/tone", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
class ToneController {
    private final ToneService toneService;
    private final RbtApi rbtApi;

    @Operation(summary = "更新彩铃列表", description = "更新彩铃列表.")
    @GetMapping("/update")
    GeneralResponse<Integer> update() throws IOException, ApiException {
        log.debug("update");
        return GeneralResponse.ok(toneService.update());
    }

    @Operation(summary = "获得彩铃列表", description = "获得彩铃列表.")
    @PostMapping("/list")
    GeneralResponse<PageableResponse<ToneInfo>> list(@Valid @RequestBody final TonePageableRequest request) {
        log.debug("list: {}", request);
        return GeneralResponse.ok(PageableResponse.of(toneService.list(request.toPageable())));
    }

    @Operation(summary = "彩铃试听", description = "该接口用于获取试听文件.")
    @PostMapping("/get")
    GeneralResponse<byte[]> get(@Valid @RequestBody final UserController.ResourceRequest request)
            throws IOException, ApiException {
        log.debug("get: {}", request);
        return GeneralResponse.ok(rbtApi.tone().get(request.getResourceId()));
    }

    public static final class TonePageableRequest extends BasePageableRequest<TonePageableRequest> {
        private static final long serialVersionUID = -4852261149893970798L;
    }
}
