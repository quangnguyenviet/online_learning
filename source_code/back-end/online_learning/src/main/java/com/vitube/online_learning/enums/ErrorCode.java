package com.vitube.online_learning.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_EXIST(1001, "User is not exist", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1002, "User is not authenticated", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1003, "token is invalid", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
