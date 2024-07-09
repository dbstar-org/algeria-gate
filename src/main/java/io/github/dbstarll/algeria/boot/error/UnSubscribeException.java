package io.github.dbstarll.algeria.boot.error;

import org.apache.hc.core5.http.HttpStatus;

public final class UnSubscribeException extends AlgeriaException {
    private static final long serialVersionUID = 1015245240373107500L;

    /**
     * 构建UnSubscribeException.
     *
     * @param message 异常消息
     * @param vip     是否VIP
     */
    public UnSubscribeException(final String message, final boolean vip) {
        super(message);
        setData(data -> data.put("vip", vip));
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
