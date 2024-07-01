package io.github.dbstarll.algeria.boot.model.response;

import io.github.dbstarll.algeria.boot.error.ExceptionData;
import io.github.dbstarll.algeria.boot.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * @param <D> 数据的类型
 * @author dbstar
 */
@Getter
public final class GeneralResponse<D> extends BaseModel {
    private static final long serialVersionUID = -4774767100968816721L;

    @Schema(description = "错误码：0为正常，其他为异常")
    private final int code;
    @Schema(description = "错误消息：仅在code!=0时有值")
    private final String message;
    @Schema(description = "错误类型：仅在code!=0时有值")
    private final String type;
    @Schema(description = "主体数据")
    private final transient D data;

    private GeneralResponse(final int code, final String message, final String type, final D data) {
        this.code = code;
        this.message = message;
        this.type = type;
        this.data = data;
    }

    /**
     * 构建一个正常的GeneralResponse.
     *
     * @param data response主体数据.
     * @param <T>  主体数据的类型
     * @return GeneralResponse
     */
    public static <T> GeneralResponse<T> ok(final T data) {
        return new GeneralResponse<>(0, null, null, data);
    }

    /**
     * 构建一个基于异常信息的GeneralResponse.
     *
     * @param exception 异常信息
     * @param fnCode    获得异常码的函数
     * @param fnMessage 获得异常消息的函数
     * @param fnType    获得异常类型的函数
     * @param fnData    获得主体数据的函数
     * @param <E>       异常的类型
     * @param <T>       主体数据的类型
     * @return GeneralResponse
     */
    public static <E extends Exception, T> GeneralResponse<T> exception(
            final E exception, final ToIntFunction<E> fnCode, final Function<E, String> fnMessage,
            final Function<E, String> fnType, final Function<E, T> fnData) {
        return new GeneralResponse<>(fnCode.applyAsInt(exception), fnMessage.apply(exception),
                fnType.apply(exception), fnData.apply(exception));
    }

    /**
     * 构建一个基于异常信息的GeneralResponse.
     *
     * @param exception 异常信息
     * @param fnCode    获得异常码的函数
     * @param fnData    获得主体数据的函数
     * @param <E>       异常的类型
     * @param <T>       主体数据的类型
     * @return GeneralResponse
     */
    public static <E extends Exception, T> GeneralResponse<T> exception(
            final E exception, final ToIntFunction<E> fnCode, final Function<E, T> fnData) {
        return exception(exception, fnCode, Throwable::getMessage, GeneralResponse::typeOfClassSimpleName, fnData);
    }

    /**
     * 构建一个基于异常信息的GeneralResponse.
     *
     * @param exception 异常信息
     * @param data      主体数据
     * @param <E>       异常的类型
     * @param <T>       主体数据的类型
     * @return GeneralResponse
     */
    public static <E extends Exception, T extends ExceptionData<E>> GeneralResponse<T> exception(
            final E exception, final T data) {
        return exception(exception, data::code, data::message, data::type, e -> data);
    }

    /**
     * 采用异常类的简单类名作为异常类型.
     *
     * @param exception 异常信息
     * @param <E>       异常的类型
     * @return 异常类的简单类名
     */
    public static <E extends Exception> String typeOfClassSimpleName(final E exception) {
        return exception.getClass().getSimpleName();
    }
}
