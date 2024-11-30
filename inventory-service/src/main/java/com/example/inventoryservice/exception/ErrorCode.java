package com.example.inventoryservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(1016, "Product not found", HttpStatus.NOT_FOUND),
    INSUFFICIENT_STOCK(1017,"Insufficient product",HttpStatus.BAD_REQUEST),
    EVENT_NOT_FOUND_EXCEPTION(2000,"Request invalid or already been deleted",HttpStatus.NOT_FOUND);
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
