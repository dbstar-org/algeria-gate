package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.github.dbstarll.algeria.boot.uuid.Uuid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Tag(name = "登录管理")
@RestController
@RequestMapping(path = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
class LoginController {
    @Operation(summary = "手机+验证码登录", description = "手机+验证码登录.")
    @PostMapping("/phone")
    UUID login(@Valid @RequestBody final LoginRequest request) {
        return Uuid.generate();
    }

    @Getter
    @Setter
    public static final class LoginRequest extends BaseModel {
        private static final long serialVersionUID = -5973775058846533724L;

        @NotBlank
        @Schema(description = "手机号码")
        private String phone;

        @NotBlank
        @Schema(description = "手机验证码")
        private String verifyCode;
    }
}
