package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.component.AlgeriaGateProperties;
import io.github.dbstarll.algeria.boot.error.InvalidAccessTokenException;
import io.github.dbstarll.algeria.boot.jpa.entity.Game;
import io.github.dbstarll.algeria.boot.jpa.repository.GameRepository;
import io.github.dbstarll.algeria.boot.mdc.AccessTokenHolder;
import io.github.dbstarll.algeria.boot.model.response.BasePageableRequest;
import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import io.github.dbstarll.algeria.boot.model.response.PageableResponse;
import io.github.dbstarll.algeria.boot.model.service.SessionTimeData;
import io.github.dbstarll.algeria.boot.service.GameService;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    private final GameRepository gameRepository;

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
            try (ZipFile zipFile = new ZipFile(tmpFile)) {
                return GeneralResponse.ok(gameService.create(zipFile).getKey());
            }
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

    @Operation(summary = "获得游戏列表", description = "分页查询游戏列表.")
    @PostMapping("/list")
    GeneralResponse<PageableResponse<Game>> list(@Valid @RequestBody final GamePageableRequest request) {
        log.debug("list: {}", request);
        return GeneralResponse.ok(PageableResponse.of(gameRepository.findAllByVip(request.vip, request.toPageable())));
    }

    @Operation(summary = "下载游戏", description = "下载游戏.")
    @GetMapping("/download/{gameId}")
    ResponseEntity<Resource> download(@RequestHeader(AccessTokenHolder.HEADER_ACCESS_TOKEN) final UUID token,
                                      @PathVariable final UUID gameId) {
        log.debug("download: {}", Uuid.toString(gameId));
        userService.verify(token, true);
        final Game game = gameRepository.getReferenceById(gameId);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(game.getSize());
        headers.setContentDispositionFormData("attachment", game.getBin());
        return ResponseEntity.ok().headers(headers).body(new FileSystemResource(gameService.file(game)));
    }

    @Setter
    public static final class GamePageableRequest extends BasePageableRequest<GamePageableRequest> {
        private static final long serialVersionUID = -3352945452791685812L;

        @Schema(description = "是否VIP游戏")
        private boolean vip;
    }
}
