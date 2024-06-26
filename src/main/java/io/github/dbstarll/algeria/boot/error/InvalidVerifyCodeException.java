package io.github.dbstarll.algeria.boot.error;

import org.apache.hc.core5.http.HttpStatus;

public final class InvalidVerifyCodeException extends AlgeriaException {
    private static final long serialVersionUID = 4763241449969696014L;

    /**
     * 构建InvalidVerifyCodeException.
     *
     * @param message 异常消息
     */
    public InvalidVerifyCodeException(final String message) {
        super(message);
    }

    @Override
    public int status() {
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public int code() {
        return ErrorCodes.INVALID_VERIFY_CODE;
    }
}
