package io.bhimsur.librarian.exception;

import io.bhimsur.librarian.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class LibrarianExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorDto> genericException(GenericException e) {
        ErrorDto errorDto = new ErrorDto(e.getErrorCode(), e.getErrorMessage(), MDC.get("X-B3-TraceId"));
        return new ResponseEntity<>(errorDto, HttpStatus.valueOf(e.getHttpStatus()));
    }
}
