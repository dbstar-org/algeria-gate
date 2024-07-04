package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.error.InvalidVerifyCodeException;
import io.github.dbstarll.algeria.boot.mdc.AccessTokenHolder;
import io.github.dbstarll.algeria.boot.model.BaseModel;
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
import java.util.Collections;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;

@Tag(name = "登录管理")
@RestController
@RequestMapping(path = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
class LoginController {
    private final UserService userService;

    @Operation(summary = "发送验证码", description = "向指定的手机发送随机验证码")
    @PostMapping("/verify-code")
    GeneralResponse<Map<String, Long>> verifyCode(@Valid @RequestBody final PhoneRequest request)
            throws IOException, ApiException {
        log.debug("verifyCode: {}", request);
        return GeneralResponse.ok(Collections.singletonMap("wait", userService.obtainVerifyCode(request.getPhone())));
    }

    @Operation(summary = "手机+验证码登录", description = "手机+验证码登录并获得token.")
    @PostMapping("/phone")
    GeneralResponse<UUID> login(@Valid @RequestBody final LoginRequest request) throws IOException, ApiException {
        log.debug("login: {}", request);
        if (!userService.verification(request.getPhone(), request.getVerifyCode()).isPresent()) {
            throw new InvalidVerifyCodeException("验证码错误");
        } else {
            return GeneralResponse.ok(userService.login(request.getPhone()));
        }
    }

    @Operation(summary = "退出登录", description = "退出当前登录并使token失效.")
    @GetMapping("/logout")
    GeneralResponse<SessionTimeData> logout(@RequestHeader(AccessTokenHolder.HEADER_ACCESS_TOKEN) final UUID token) {
        log.debug("logout: {}", Uuid.toString(token));
        userService.verify(token, false);
        return GeneralResponse.ok(userService.logout(token));
    }

    @Getter
    @Setter
    public static final class LoginRequest extends PhoneRequest {
        private static final long serialVersionUID = -5973775058846533724L;

        @NotBlank
        @Schema(description = "手机验证码")
        private String verifyCode;

        @Override
        protected StringJoiner addToStringEntry(final StringJoiner joiner) {
            return super.addToStringEntry(joiner).add("verifyCode=" + getVerifyCode());
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneRequest extends BaseModel {
        private static final long serialVersionUID = -7290745084930332046L;

        @NotBlank
        @Schema(description = "手机号码")
        private String phone;

        @Override
        protected StringJoiner addToStringEntry(final StringJoiner joiner) {
            return super.addToStringEntry(joiner).add("phone=" + getPhone());
        }
    }
}
