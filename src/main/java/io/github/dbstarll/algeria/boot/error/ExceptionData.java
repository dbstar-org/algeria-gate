package io.github.dbstarll.algeria.boot.error;

import org.springframework.http.HttpStatus;

/**
 * @param <E> 异常类型
 * @author dbstar
 */
public interface ExceptionData<E extends Exception> {
    /**
     * 获得错误码，0为正常，其他为异常.
     *
     * @param e exception
     * @return 错误码
     */
    default int code(E e) {
        return ErrorCodes.UNKNOWN;
    }

    /**
     * get HttpStatus of ErrorCode.
     *
     * @param e exception
     * @return HttpStatus
     */
    default HttpStatus status(E e) {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * 获得错误消息.
     *
     * @param e exception
     * @return 错误消息
     */
    default String message(E e) {
        return e.getMessage();
    }

    /**
     * 获得错误类型.
     *
     * @param e exception
     * @return 错误类型
     */
    default String type(E e) {
        return e.getClass().getSimpleName();
    }
}
