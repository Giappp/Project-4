package com.example.beskbd.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1000, "Uncategorized Exception", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(1008, "Token has expired", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN(1009, "Token format is not supported", HttpStatus.FORBIDDEN),
    MALFORMED_TOKEN(1010, "Token is malformed", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1011, "Not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(111,"Not found Order" ,HttpStatus.NOT_FOUND );

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
