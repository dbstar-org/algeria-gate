package io.github.dbstarll.algeria.boot.error;

import lombok.Getter;

import static io.github.dbstarll.algeria.boot.api.RbtApi.RbtApiException;

/**
 * @author dbstar
 */
@Getter
public final class RbtApiExceptionData implements ExceptionData<RbtApiException> {
    private final String returnCode;

    /**
     * 构建BindingResultData.
     *
     * @param e RbtApiException
     */
    public RbtApiExceptionData(final RbtApiException e) {
        this.returnCode = e.getReturnCode();
    }

    @Override
    public int code(final RbtApiException e) {
        return ErrorCodes.RBT_API_FAILED;
    }
}
