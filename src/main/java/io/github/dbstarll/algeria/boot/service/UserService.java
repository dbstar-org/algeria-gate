package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.utils.net.api.ApiException;

import java.io.IOException;
import java.util.UUID;

public interface UserService {
    /**
     * 向指定的手机发送随机验证码.
     *
     * @param phone 手机号码
     */
    void verifyCode(String phone) throws IOException, ApiException;

    UUID login(String phone, String verifyCode);
}
