package com.vitube.online_learning.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * Entry point tùy chỉnh để xử lý truy cập không được phép.
 * Lớp này được kích hoạt khi người dùng chưa xác thực cố gắng truy cập tài nguyên được bảo vệ.
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Xử lý các yêu cầu truy cập không được phép bằng cách trả về phản hồi JSON chứa thông tin lỗi.
     *
     * @param request       Đối tượng yêu cầu HTTP.
     * @param response      Đối tượng phản hồi HTTP.
     * @param authException Ngoại lệ xảy ra khi xác thực thất bại.
     * @throws IOException      Nếu xảy ra lỗi I/O.
     * @throws ServletException Nếu xảy ra lỗi servlet.
     */
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // Lấy mã lỗi cho truy cập chưa xác thực
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        // Thiết lập trạng thái HTTP và kiểu nội dung phản hồi
        response.setStatus(errorCode.getStatusCode().value());
        response.setHeader("Content-Type", "application/json");

        // Xây dựng đối tượng phản hồi API với thông tin lỗi
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(errorCode.getCode())
                .data(errorCode.getMessage())
                .build();

        // Chuyển đổi đối tượng ApiResponse thành chuỗi JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        // Ghi chuỗi JSON vào nội dung phản hồi HTTP
        response.getWriter().write(jsonResponse);
        response.flushBuffer(); // Gửi phản hồi đến client
    }
}