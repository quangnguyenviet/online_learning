package com.vitube.online_learning.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

/**
 * Enum đại diện cho các mã lỗi trong hệ thống.
 * Mỗi mã lỗi bao gồm mã số, thông báo và mã trạng thái HTTP tương ứng.
 */
@Getter
public enum ErrorCode {
    /**
     * Lỗi: Người dùng không tồn tại.
     */
    USER_NOT_EXIST(1001, "User is not exist", HttpStatus.BAD_REQUEST),

    /**
     * Lỗi: Người dùng chưa xác thực.
     */
    UNAUTHENTICATED(1002, "User is not authenticated", HttpStatus.UNAUTHORIZED),

    /**
     * Lỗi: Token không hợp lệ.
     */
    INVALID_TOKEN(1003, "token is invalid", HttpStatus.BAD_REQUEST),

    /**
     * Lỗi: Tên người dùng đã tồn tại.
     */
    USERNAME_EXIST(1004, "Username is already exist", HttpStatus.BAD_REQUEST),

    /**
     * Lỗi: Email đã tồn tại.
     */
    EMAIL_EXIST(1005, "Email is already exist", HttpStatus.BAD_REQUEST),

    /**
     * Lỗi: Email không hợp lệ.
     */
    INVALID_EMAIL(1006, "Email is invalid", HttpStatus.BAD_REQUEST),

    /**
     * Lỗi: Tên người dùng không hợp lệ.
     */
    INVALID_USERNAME(1007, "Username is invalid", HttpStatus.BAD_REQUEST),

    /**
     * Lỗi: Mật khẩu không hợp lệ.
     */
    INVALID_PASSWORD(1008, "Password is invalid", HttpStatus.BAD_REQUEST),

    /**
     * Lỗi: Vai trò không tìm thấy.
     */
    ROLE_NOT_FOUND(1009, "Role is not found", HttpStatus.BAD_REQUEST),
    ;

    /**
     * Hàm khởi tạo cho enum ErrorCode.
     *
     * @param code Mã số lỗi.
     * @param message Thông báo lỗi.
     * @param statusCode Mã trạng thái HTTP.
     */
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    /**
     * Mã số lỗi.
     */
    private final int code;

    /**
     * Thông báo lỗi.
     */
    private final String message;

    /**
     * Mã trạng thái HTTP tương ứng.
     */
    private final HttpStatusCode statusCode;
}