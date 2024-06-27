package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import io.github.dbstarll.algeria.boot.model.service.SessionTimeData;
import io.github.dbstarll.algeria.boot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "用户管理")
@RestController
@RequestMapping(path = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @Operation(summary = "验证token", description = "检查token是否有效，并返回用户信息.")
    @GetMapping("/verify")
    GeneralResponse<SessionTimeData> verify(@RequestHeader("gate-access-token") final UUID token) {
        return GeneralResponse.ok(userService.verify(token));
    }
}
