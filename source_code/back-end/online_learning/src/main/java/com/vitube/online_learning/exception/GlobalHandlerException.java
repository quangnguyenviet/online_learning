package com.vitube.online_learning.exception;

import com.vitube.online_learning.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> apiExceptionHandler(AppException e){
        ApiResponse<String> response = ApiResponse.<String>builder()
                .data(e.getMessage())
                .status(e.getErrorCode().getCode())
                .build();
        return ResponseEntity.status(e.getErrorCode().getStatusCode()).body(response);
    }

}
