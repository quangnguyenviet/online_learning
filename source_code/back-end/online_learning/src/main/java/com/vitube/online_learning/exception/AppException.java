package com.vitube.online_learning.exception;

import com.vitube.online_learning.enums.ErrorCode;
import lombok.Data;

@Data
public class AppException extends RuntimeException {
    public AppException(ErrorCode errorCode) {
      super(errorCode.getMessage());
      this.errorCode = errorCode;
  }
    private ErrorCode errorCode;
}
