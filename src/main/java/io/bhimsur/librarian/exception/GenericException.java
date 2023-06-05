package io.bhimsur.librarian.exception;

import io.bhimsur.librarian.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {
    private static final long serialVersionUID = -138638807285891189L;

    private final Integer errorCode;
    private final String errorMessage;
    private final int httpStatus;
    public GenericException(String message) {
        super(message);
        this.errorCode = null;
        this.errorMessage = message;
        this.httpStatus = HttpStatus.BAD_REQUEST.value();

    }

    public GenericException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public GenericException(int errorCode, String errorMessage, int httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
