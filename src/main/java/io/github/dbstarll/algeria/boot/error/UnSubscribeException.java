package io.github.dbstarll.algeria.boot.error;

import org.apache.hc.core5.http.HttpStatus;

public final class UnSubscribeException extends AlgeriaException {
    private static final long serialVersionUID = 1015245240373107500L;

    /**
     * 构建UnSubscribeException.
     *
     * @param message 异常消息
     */
    public UnSubscribeException(final String message) {
        super(message);
    }

    @Override
    public int status() {
        return HttpStatus.SC_FORBIDDEN;
    }

    @Override
    public int code() {
        return ErrorCodes.UN_SUBSCRIBE;
    }
}
