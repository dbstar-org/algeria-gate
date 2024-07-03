package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.api.RbtApi;
import io.github.dbstarll.algeria.boot.mdc.AccessTokenHolder;
import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.github.dbstarll.algeria.boot.model.api.response.user.ContentDownloadInfo;
import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import io.github.dbstarll.algeria.boot.model.service.SessionTimeData;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.github.dbstarll.utils.net.api.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

@Tag(name = "用户管理")
@RestController
@RequestMapping(path = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class UserController {
    private final UserService userService;
    private final RbtApi rbtApi;

    @Operation(summary = "验证token", description = "检查token是否有效，并返回用户信息.")
    @GetMapping("/verify")
    GeneralResponse<SessionTimeData> verify(@RequestHeader(AccessTokenHolder.HEADER_ACCESS_TOKEN) final UUID token) {
        log.debug("verify: {}", Uuid.toString(token));
        return GeneralResponse.ok(userService.verify(token, false));
    }

    @Operation(summary = "开通并下载铃音", description = "该接口用于通过下载铃音或音乐盒开通RBT业务特性.")
    @PostMapping("/easy-download")
    GeneralResponse<List<ContentDownloadInfo>> easyDownload(
            @RequestHeader(AccessTokenHolder.HEADER_ACCESS_TOKEN) final UUID token,
            @Valid @RequestBody final ResourceRequest request) throws IOException, ApiException {
        log.debug("easyDownload: {}", request);
        final SessionTimeData session = userService.verify(token, true);
        return GeneralResponse.ok(rbtApi.user().easyDownload(session.getPhone(),
                request.getResourceId()).getContentDownloadInfo());
    }

    @Operation(summary = "退订铃音", description = "该接口用于用户退订铃音/铃音盒.")
    @PostMapping("/del-inbox-tone")
    GeneralResponse<String> delInboxTone(
            @RequestHeader(AccessTokenHolder.HEADER_ACCESS_TOKEN) final UUID token,
            @Valid @RequestBody final ResourceRequest request) throws IOException, ApiException {
        log.debug("delInboxTone: {}", request);
        final SessionTimeData session = userService.verify(token, true);
        return GeneralResponse.ok(rbtApi.user().tone().delInboxTone(session.getPhone(),
                request.getResourceId()).getResultInfo());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResourceRequest extends BaseModel {
        private static final long serialVersionUID = -3231339152237009338L;

        @NotBlank
        @Schema(description = "铃音的内部ID")
        private String resourceId;

        @Override
        protected StringJoiner addToStringEntry(final StringJoiner joiner) {
            return super.addToStringEntry(joiner).add("resourceId=" + getResourceId());
        }
    }
}
