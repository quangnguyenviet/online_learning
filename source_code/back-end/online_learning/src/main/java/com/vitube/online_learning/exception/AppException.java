package com.vitube.online_learning.exception;

import com.vitube.online_learning.enums.ErrorCode;

import lombok.Getter;
import lombok.Setter;

/**
 * Lớp ngoại lệ tùy chỉnh cho ứng dụng.
 * Được sử dụng để xử lý các lỗi với mã lỗi cụ thể.
 */
@Getter
@Setter
public class AppException extends RuntimeException {
    /**
     * Mã lỗi liên quan đến ngoại lệ.
     */
    private ErrorCode errorCode;

    /**
     * Hàm khởi tạo ngoại lệ với mã lỗi.
     *
     * @param errorCode Mã lỗi được truyền vào.
     */
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}