package io.github.dbstarll.algeria.boot.error;

import io.github.dbstarll.algeria.boot.model.BaseModel;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dbstar
 */
public final class BindExceptionData implements ExceptionData<BindException> {
    private final BindingResult bindingResult;

    /**
     * 构建BindingResultData.
     *
     * @param e BindException
     */
    public BindExceptionData(final BindException e) {
        this.bindingResult = e.getBindingResult();
    }

    @Override
    public int code(final BindException e) {
        return ErrorCodes.VALIDATION_FAILED;
    }

    @Override
    public String message(final BindException e) {
        return "Validation failed";
    }

    /**
     * Return the number of global errors.
     *
     * @return the number of global errors
     */
    public int getGlobalErrorCount() {
        return bindingResult.getGlobalErrorCount();
    }

    /**
     * Get all global errors.
     *
     * @return a list of {@link ObjectErrorData} instances
     */
    public List<ObjectErrorData> getGlobalErrors() {
        return Optional.of(bindingResult.getGlobalErrors())
                .filter(errors -> bindingResult.hasGlobalErrors())
                .map(errors -> errors.stream().map(ObjectErrorData::new).collect(Collectors.toList()))
                .orElse(null);
    }

    /**
     * Return the number of errors associated with a field.
     *
     * @return the number of errors associated with a field
     */
    public int getFieldErrorCount() {
        return bindingResult.getFieldErrorCount();
    }

    /**
     * Get all errors associated with a field.
     *
     * @return a List of {@link FieldErrorData} instances
     */
    public List<FieldErrorData> getFieldErrors() {
        return Optional.of(bindingResult.getFieldErrors())
                .filter(errors -> bindingResult.hasFieldErrors())
                .map(errors -> errors.stream().map(FieldErrorData::new).collect(Collectors.toList()))
                .orElse(null);
    }

    /**
     * @author dbstar
     */
    public static class FieldErrorData extends ObjectErrorData {
        private static final long serialVersionUID = -8738013458164382203L;

        private final FieldError fieldError;

        /**
         * 构建FieldErrorData.
         *
         * @param fieldError FieldError
         */
        public FieldErrorData(final FieldError fieldError) {
            super(fieldError);
            this.fieldError = fieldError;
        }

        /**
         * Return the affected field of the object.
         *
         * @return the affected field of the object
         */
        public final String getField() {
            return fieldError.getField();
        }
    }

    /**
     * @author dbstar
     */
    public static class ObjectErrorData extends BaseModel {
        private static final long serialVersionUID = 1416024647257108411L;

        private final ObjectError objectError;

        /**
         * 构建ObjectErrorData.
         *
         * @param objectError ObjectError
         */
        public ObjectErrorData(final ObjectError objectError) {
            this.objectError = objectError;
        }

        /**
         * Return the name of the affected object.
         *
         * @return the name of the affected object
         */
        public final String getObjectName() {
            return objectError.getObjectName();
        }

        /**
         * Return the default code of this resolvable, that is,
         * the last one in the codes array.
         *
         * @return the default code of this resolvable
         */
        public final String getCode() {
            return objectError.getCode();
        }

        /**
         * Return the default message to be used to resolve this message.
         *
         * @return the default message, or {@code null} if no default
         */
        public final String getDefaultMessage() {
            return objectError.getDefaultMessage();
        }
    }
}
