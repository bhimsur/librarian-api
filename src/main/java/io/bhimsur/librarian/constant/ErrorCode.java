package io.bhimsur.librarian.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_REQUEST(0, "Invalid Request", HttpStatus.BAD_REQUEST.value()),
    BOOK_ALREADY_EXISTS(1, "Book Already Exists", HttpStatus.BAD_REQUEST.value()),
    MEMBER_ALREADY_EXISTS(2, "Member Already Exists", HttpStatus.BAD_REQUEST.value()),
    DATA_NOT_FOUND(3, "Data Not Found", HttpStatus.NOT_FOUND.value()),
    BOOK_OUT_OF_STOCK(4, "Out of Stock", HttpStatus.BAD_REQUEST.value()),
    MEMBER_NOT_REGISTERED(5, "Member Not Registered", HttpStatus.BAD_REQUEST.value()),
    ALREADY_EXECUTED(6, "Already Executed", HttpStatus.BAD_REQUEST.value()),
    INVALID_TRANSACTION(7, "Invalid Transaction", HttpStatus.BAD_REQUEST.value()),
    ;

    private final int code;
    private final String message;
    private final int httpStatus;
}
