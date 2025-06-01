package com.vitube.online_learning.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.service.RegisterService;
import com.vitube.online_learning.service.ZaloPayService;
import com.vitube.online_learning.utils.HMACUtil;

import lombok.RequiredArgsConstructor;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến thanh toán qua ZaloPay.
 * Bao gồm các API để tạo đơn hàng và xử lý callback từ ZaloPay.
 */
@RestController
@RequestMapping("/zalopay")
@RequiredArgsConstructor
public class ZaloPayController {

    private final ZaloPayService zaloPayService;
    private final RegisterService registerService;

    @Value("${zalopay.endpoint}")
    private String ZALOPAY_ENDPOINT;

    @Value("${zalopay.key2}")
    private String KEY_2;

    /**
     * API tạo đơn hàng thanh toán qua ZaloPay.
     * Nhận ID khóa học từ yêu cầu và gửi thông tin đơn hàng đến ZaloPay.
     *
     * @param courseId ID của khóa học cần thanh toán.
     * @return Phản hồi từ ZaloPay chứa thông tin đơn hàng.
     * @throws Exception Nếu xảy ra lỗi trong quá trình tạo đơn hàng.
     */
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestParam String courseId) throws Exception {
        Map<String, Object> requestData = zaloPayService.createOrder(courseId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        StringBuilder form = new StringBuilder();
        for (Map.Entry<String, Object> entry : requestData.entrySet()) {
            form.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            form.append("=");
            form.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
            form.append("&");
        }
        form.setLength(form.length() - 1); // Xóa ký tự & cuối cùng

        HttpEntity<String> entity = new HttpEntity<>(form.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(ZALOPAY_ENDPOINT, entity, String.class);

        return response;
    }

    /**
     * API xử lý callback từ ZaloPay.
     * Xác thực thông tin callback và lưu thông tin đăng ký khóa học vào hệ thống.
     *
     * @param payload Dữ liệu callback từ ZaloPay.
     * @return Phản hồi xác nhận xử lý callback thành công hoặc thất bại.
     */
    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestBody Map<String, Object> payload) {
        try {
            // Lấy các trường cần thiết từ payload
            String data = (String) payload.get("data");
            String reqMac = (String) payload.get("mac");

            // Tính lại MAC để xác thực (dùng key2)
            String calculatedMac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, KEY_2, data);

            if (!calculatedMac.equals(reqMac)) {
                return ResponseEntity.badRequest().body("Invalid MAC");
            }

            // Parse data JSON để lấy thông tin đơn hàng
            JSONObject dataJson = new JSONObject(data);
            String appTransId = dataJson.getString("app_trans_id");

            Float amount = dataJson.getFloat("amount");
            long appTime = dataJson.getLong("app_time");
            Date registerDate = new Date(appTime);

            System.out.println("thanh toan thanh cong");
            String embedDataStr = dataJson.getString("embed_data"); // lấy chuỗi JSON từ key "embed_data"
            JSONObject embedDataJson = new JSONObject(embedDataStr); // chuyển thành JSONObject
            String courseId = embedDataJson.getString("courseId"); // lấy ra giá trị courseId

            // Thêm thông tin đăng ký khóa học
            registerService.createRegisterData(RegisterRequest.builder()
                    .price(amount)
                    .registerDate(registerDate)
                    .courseId(courseId)
                    .studentId(dataJson.getString("app_user"))
                    .build());

            // Trả về cho ZaloPay biết rằng bạn đã xử lý callback thành công
            return ResponseEntity.ok("{\"return_code\":1, \"return_message\":\"Success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"return_code\":0, \"return_message\":\"Server Error\"}");
        }
    }
}