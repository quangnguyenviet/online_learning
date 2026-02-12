package com.vitube.online_learning.exception;

import com.vitube.online_learning.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Lớp xử lý ngoại lệ toàn cục cho ứng dụng.
 * Được sử dụng để bắt và xử lý các ngoại lệ xảy ra trong ứng dụng.
 */
@ControllerAdvice
public class GlobalHandlerException {
    /**
     * Xử lý ngoại lệ kiểu RuntimeException.
     *
     * @param e Ngoại lệ RuntimeException.
     * @return Đối tượng ResponseEntity chứa thông báo lỗi.
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Xử lý ngoại lệ kiểu AppException.
     *
     * @param e Ngoại lệ AppException.
     * @return Đối tượng ResponseEntity chứa thông tin lỗi dưới dạng ApiResponse.
     */
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> apiExceptionHandler(AppException e) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .message(e.getMessage())
                .status(e.getErrorCode().getCode())
                .build();
        return ResponseEntity.status(e.getErrorCode().getStatusCode()).body(response);
    }
}