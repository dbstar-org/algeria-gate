package io.github.dbstarll.algeria.boot.error;

public final class FrequentlyObtainVerifyCodeException extends AlgeriaException {
    private static final long serialVersionUID = 7294303215006917966L;

    /**
     * 构建FrequentlyObtainVerifyCodeException.
     *
     * @param phone 没有区号的手机号
     */
    public FrequentlyObtainVerifyCodeException(final String phone) {
        super("获取验证码过于频繁，请稍后再试！");
        setData(map -> map.put("phone", phone));
    }

    @Override
    public int code() {
        return ErrorCodes.FREQUENTLY_OBTAIN_VERIFY_CODE;
    }
}
