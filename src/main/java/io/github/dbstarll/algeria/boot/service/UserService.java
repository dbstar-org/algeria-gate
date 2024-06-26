package io.github.dbstarll.algeria.boot.service;

public interface UserService {
    /**
     * 向指定的手机发送随机验证码.
     *
     * @param mobile 手机号码
     */
    void verifyCode(String mobile);
}
