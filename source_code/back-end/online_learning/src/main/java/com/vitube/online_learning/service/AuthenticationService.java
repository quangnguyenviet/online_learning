package com.vitube.online_learning.service;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.vitube.online_learning.dto.request.AuthenticationRequest;
import com.vitube.online_learning.dto.request.IntrospectRequest;
import com.vitube.online_learning.dto.request.LogoutRequest;
import com.vitube.online_learning.dto.request.RefreshRequest;
import com.vitube.online_learning.dto.response.AuthenticationResponse;
import com.vitube.online_learning.dto.response.IntrospectRespone;

/**
 * Interface cung cấp các phương thức liên quan đến xác thực.
 */
public interface AuthenticationService {
    /**
     * Xác thực người dùng dựa trên yêu cầu.
     *
     * @param request Yêu cầu xác thực.
     * @return Đối tượng phản hồi xác thực.
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

    /**
     * Kiểm tra token và trả về thông tin introspect.
     *
     * @param request Yêu cầu introspect.
     * @return Đối tượng phản hồi introspect.
     * @throws JOSEException Lỗi liên quan đến JOSE.
     * @throws ParseException Lỗi phân tích cú pháp.
     */
    IntrospectRespone introspect(IntrospectRequest request) throws JOSEException, ParseException;

    /**
     * Đăng xuất người dùng dựa trên yêu cầu.
     *
     * @param request Yêu cầu đăng xuất.
     * @return Đối tượng phản hồi đăng xuất.
     */
    Object logout(LogoutRequest request);

    /**
     * Làm mới token dựa trên yêu cầu.
     *
     * @param request Yêu cầu làm mới token.
     * @return Đối tượng phản hồi xác thực với token mới.
     */
    AuthenticationResponse refreshToken(RefreshRequest request);
}