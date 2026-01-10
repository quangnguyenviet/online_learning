package com.vitube.online_learning.controller;

import java.text.ParseException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.vitube.online_learning.dto.request.AuthenticationRequest;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.dto.request.LogoutRequest;
import com.vitube.online_learning.dto.request.RefreshRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.AuthenticationService;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến xác thực.
 * Bao gồm các API để đăng nhập, introspect token, đăng xuất, và làm mới token.
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * API đăng nhập.
     * Nhận thông tin đăng nhập từ người dùng và trả về token xác thực.
     *
     * @param request Đối tượng chứa thông tin đăng nhập.
     * @return Phản hồi API chứa token xác thực.
     */
    @PostMapping("/login")
    public ApiResponse<?> authenticate(@RequestBody @Valid AuthenticationRequest request) {

        return ApiResponse.builder()
                .status(1000)
                .data(authenticationService.authenticate(request))
                .build();
    }

    /**
     * API introspect token.
     * Kiểm tra tính hợp lệ của token được cung cấp.
     *
     * @param request Đối tượng chứa token cần kiểm tra.
     * @return Phản hồi API chứa kết quả kiểm tra token.
     * @throws ParseException Nếu xảy ra lỗi khi phân tích token.
     * @throws JOSEException Nếu xảy ra lỗi liên quan đến JOSE.
     */
    @PostMapping("/introspect")
    public ApiResponse<?> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.builder()
                .status(1000)
                .data(authenticationService.introspect(request))
                .build();
    }

    /**
     * API đăng xuất.
     * Xóa token của người dùng khỏi hệ thống.
     *
     * @param request Đối tượng chứa thông tin đăng xuất.
     * @return Phản hồi API xác nhận đăng xuất thành công.
     */
    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestBody LogoutRequest request) {
        authenticationService.logout(request);
        return ApiResponse.builder().status(1000).build();
    }

    /**
     * API làm mới token.
     * Tạo token mới dựa trên token cũ đã hết hạn.
     *
     * @param request Đối tượng chứa thông tin làm mới token.
     * @return Phản hồi API chứa token mới.
     */
    @PostMapping("/refresh-token")
    public ApiResponse<?> refreshtoken(@RequestBody RefreshRequest request) throws ParseException, JOSEException {

        return ApiResponse.builder()
                .status(1000)
                .data(authenticationService.refreshToken(request))
                .build();
    }
}