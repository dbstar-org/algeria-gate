package io.github.dbstarll.algeria.boot.error;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.core.NestedRuntimeException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AlgeriaException extends NestedRuntimeException {
    private static final long serialVersionUID = 1782643877654940576L;

    private final Map<String, Serializable> data = new HashMap<>();

    protected AlgeriaException(String msg) {
        super(msg);
    }

    protected AlgeriaException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 获得状态码.
     *
     * @return 状态码
     */
    public int status() {
        return HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

    /**
     * 获得错误码.
     *
     * @return 错误码
     */
    public int code() {
        return ErrorCodes.UNKNOWN;
    }

    /**
     * 获得错误数据.
     *
     * @return 错误数据
     */
    public final Map<String, Serializable> data() {
        return data.isEmpty() ? null : data;
    }

    protected final void setData(final Consumer<Map<String, Serializable>> consumer) {
        consumer.accept(this.data);
    }
}
