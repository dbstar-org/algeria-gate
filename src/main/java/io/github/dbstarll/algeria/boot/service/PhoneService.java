package io.github.dbstarll.algeria.boot.service;

import io.github.dbstarll.utils.net.api.ApiException;

import java.io.IOException;
import java.util.Optional;

public interface PhoneService {
    /**
     * 向指定的手机发送随机验证码.
     *
     * @param phone 手机号码
     * @return 下次获取等候间隔(秒)
     */
    long obtainVerifyCode(String phone) throws IOException, ApiException;

    /**
     * 验证手机验证码是否有效.
     *
     * @param phone      手机号码
     * @param verifyCode 验证码
     * @return 若有效，返回否则返回Optional.of(sign)，否则返回Optional.empty()
     */
    Optional<String> verification(String phone, String verifyCode);
}
