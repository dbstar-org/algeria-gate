package io.github.dbstarll.algeria.boot.error;

import org.apache.hc.core5.http.HttpStatus;

public class InvalidAccessTokenException extends AlgeriaException {
    private static final long serialVersionUID = 2872658752892238917L;

    /**
     * 构建InvalidAccessTokenException.
     *
     * @param message message
     */
    public InvalidAccessTokenException(final String message) {
        super(message);
    }

    @Override
    public int status() {
        return HttpStatus.SC_UNAUTHORIZED;
    }

    @Override
    public int code() {
        return ErrorCodes.INVALID_ACCESS_TOKEN;
    }
}
