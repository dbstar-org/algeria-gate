package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.error.*;
import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.util.Map;

import static io.github.dbstarll.algeria.boot.api.RbtApi.RbtApiException;


/**
 * @author dbstar
 */
@RestControllerAdvice
@Slf4j
final class ExceptionController {
    @ExceptionHandler(Exception.class)
    ResponseEntity<GeneralResponse<GeneralExceptionData>> generalExceptionHandler(final Exception e) {
        log.error("GeneralException<" + e.getClass().getSimpleName() + ">: " + e.getMessage(), e);
        return response(e, new GeneralExceptionData(e));
    }

    @ExceptionHandler(BindException.class)
    ResponseEntity<GeneralResponse<BindExceptionData>> bindExceptionHandler(final BindException e) {
        log.error("BindException", e);
        return response(e, new BindExceptionData(e));
    }

    @ExceptionHandler(RbtApiException.class)
    ResponseEntity<GeneralResponse<RbtApiExceptionData>> rbtApiExceptionHandler(final RbtApiException e) {
        log.error("RbtApiException: " + e.getMessage(), e);
        return response(e, new RbtApiExceptionData(e));
    }

    private <E extends Exception, D extends ExceptionData<E>> ResponseEntity<GeneralResponse<D>> response(
            final E e, final D data) {
        return ResponseEntity.status(data.status(e)).body(GeneralResponse.exception(e, data));
    }

    @ExceptionHandler(AlgeriaException.class)
    ResponseEntity<GeneralResponse<Map<String, Serializable>>> algeriaExceptionHandler(final AlgeriaException e) {
        log.error("algeriaException: " + e.getMessage(), e);
        return ResponseEntity.status(e.status()).body(GeneralResponse.exception(e, AlgeriaException::code, AlgeriaException::data));
    }
}
