package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import io.github.dbstarll.algeria.boot.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipFile;

@Tag(name = "游戏管理")
@RestController
@RequestMapping(path = "/api/game", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class GameController {
    private final GameService gameService;

    @Operation(summary = "上传游戏", description = "运营商上传游戏到平台.")
    @PostMapping("/upload")
    @Transactional
    public GeneralResponse<Game> upload(@RequestParam("file") final MultipartFile file) throws IOException {
        log.debug("upload name: {}, type: {}, size: {}", file.getOriginalFilename(), file.getContentType(),
                file.getSize());
        if (!"application/zip".equalsIgnoreCase(file.getContentType())) {
            throw new IOException("Unsupported file type: " + file.getContentType());
        }
        final File tmpFile = File.createTempFile("upload-game-", ".zip");
        try {
            file.transferTo(tmpFile);
            return GeneralResponse.ok(create(tmpFile));
        } finally {
            Files.delete(tmpFile.toPath());
        }
    }

    private Game create(final File tmpFile) throws IOException {
        try (ZipFile zipFile = new ZipFile(tmpFile)) {
            final Game game = gameService.create(zipFile);
            FileUtils.copyFile(tmpFile, gameService.file(game));
            return game;
        }
    }
}
