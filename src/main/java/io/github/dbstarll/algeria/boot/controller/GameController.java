package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.component.AlgeriaGateProperties;
import io.github.dbstarll.algeria.boot.error.InvalidAccessTokenException;
import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import io.github.dbstarll.algeria.boot.mdc.AccessTokenHolder;
import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import io.github.dbstarll.algeria.boot.model.service.SessionTimeData;
import io.github.dbstarll.algeria.boot.service.GameService;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipFile;

@Tag(name = "游戏管理")
@RestController
@RequestMapping(path = "/api/game", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class GameController {
    private final UserService userService;
    private final GameService gameService;
    private final AlgeriaGateProperties algeriaGateProperties;

    @Operation(summary = "上传游戏", description = "运营商上传游戏到平台.")
    @PostMapping("/upload")
    @Transactional
    public GeneralResponse<Game> upload(@RequestHeader(AccessTokenHolder.HEADER_ACCESS_TOKEN) final UUID token,
                                        @RequestParam("file") final MultipartFile file) throws IOException {
        log.debug("upload name: {}, type: {}, size: {}", file.getOriginalFilename(), file.getContentType(),
                file.getSize());
        checkAdmin(userService.verify(token, true));
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

    private void checkAdmin(final SessionTimeData session) {
        if (!Optional.ofNullable(algeriaGateProperties.getAdmin())
                .filter(admins -> admins.contains(session.getPhone()))
                .isPresent()) {
            throw new InvalidAccessTokenException("未授权");
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
