package io.github.dbstarll.algeria.boot.controller;

import io.github.dbstarll.algeria.boot.error.AlgeriaException;
import io.github.dbstarll.algeria.boot.model.response.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.util.Map;


/**
 * @author dbstar
 */
@RestControllerAdvice
@Slf4j
final class ExceptionController {
    @ExceptionHandler(AlgeriaException.class)
    ResponseEntity<GeneralResponse<Map<String, Serializable>>> algeriaExceptionHandler(final AlgeriaException e) {
        log.error("algeriaException: " + e.getMessage(), e);
        return ResponseEntity.status(e.status()).body(GeneralResponse.exception(e, AlgeriaException::code, AlgeriaException::data));
    }
}
