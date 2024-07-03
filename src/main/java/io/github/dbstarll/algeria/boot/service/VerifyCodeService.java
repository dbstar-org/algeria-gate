package io.github.dbstarll.algeria.boot.service;

public interface VerifyCodeService {
    /**
     * 生成随机验证码.
     *
     * @return 随机验证码
     */
    String generate();
}
