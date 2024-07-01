package io.github.dbstarll.algeria.boot.error;

import java.util.Optional;

/**
 * @author dbstar
 */
public final class GeneralExceptionData implements ExceptionData<Exception> {
    private final Throwable cause;

    /**
     * 构建GeneralExceptionData.
     *
     * @param e Exception
     */
    public GeneralExceptionData(final Exception e) {
        this.cause = e.getCause();
    }

    /**
     * get cause's message.
     *
     * @return cause's message
     */
    public String getCauseMessage() {
        return Optional.ofNullable(cause).map(Throwable::getMessage).orElse(null);
    }

    /**
     * get cause's type.
     *
     * @return cause's type
     */
    public String getCauseType() {
        return Optional.ofNullable(cause).map(Throwable::getClass).map(Class::getSimpleName).orElse(null);
    }
}
