package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.model.api.response.tone.ToneInfo;
import io.github.dbstarll.algeria.boot.service.ToneService;
import io.github.dbstarll.utils.net.api.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Tag(name = "彩铃管理")
@RestController
@RequestMapping(path = "/api/tone", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class ToneController {
    private final ToneService toneService;

    @Operation(summary = "更新彩铃列表", description = "更新彩铃列表.")
    @GetMapping("/update")
    int update() throws IOException, ApiException {
        return toneService.update();
    }

    @Operation(summary = "获得彩铃列表", description = "获得彩铃列表.")
    @GetMapping("/list")
    List<ToneInfo> list() {
        return toneService.list();
    }
}
