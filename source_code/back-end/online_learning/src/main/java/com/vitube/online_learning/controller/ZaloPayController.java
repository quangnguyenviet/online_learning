package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.RegisterDTO;
import com.vitube.online_learning.service.RegisterService;
import com.vitube.online_learning.service.ZaloPayService;
import com.vitube.online_learning.utils.HMACUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Lớp điều khiển xử lý các yêu cầu liên quan đến thanh toán qua ZaloPay.
 * Bao gồm các API để tạo đơn hàng và xử lý callback từ ZaloPay.
 */
@Slf4j
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


    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestParam String courseId) throws Exception {
        log.info("Inside createOrder of ZaloPayController");
        return ResponseEntity.ok(zaloPayService.createOrder(courseId));
    }

    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleCallback(
            @RequestBody Map<String, Object> payload) {

        log.info("ZaloPay callback received");
        log.info("Payload: {}", payload);

        Map<String, Object> response = new HashMap<>();

        try {
            String data = (String) payload.get("data");
            String reqMac = (String) payload.get("mac");

            String calculatedMac = HMACUtil.HMacHexStringEncode(
                    HMACUtil.HMACSHA256, KEY_2, data
            );

            if (!calculatedMac.equals(reqMac)) {
                response.put("return_code", 0);
                response.put("return_message", "Invalid MAC");
                return ResponseEntity.ok(response); // STILL 200
            }

            JSONObject dataJson = new JSONObject(data);
            String appTransId = dataJson.getString("app_trans_id");
            String appUser = dataJson.getString("app_user");
            BigDecimal price = BigDecimal.valueOf(dataJson.getLong("amount")).divide(BigDecimal.valueOf(100));

            // get courseId from embeded_data
            String embededDataStr = dataJson.getString("embed_data");
            JSONObject embededData = new JSONObject(embededDataStr);
            String courseId = embededData.getString("courseId");

            // create register data
            RegisterDTO registerDTO = RegisterDTO.builder()
                    .courseId(courseId)
                    .studentEmail(appUser)
                    .price(price)
                    .build();

            log.info("RegisterDTO: {}", registerDTO);

            // update register table
            registerService.createRegisterData(registerDTO);


            response.put("return_code", 1);
            response.put("return_message", "Success");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Callback error", e);
            response.put("return_code", 0);
            response.put("return_message", "Server error");
            return ResponseEntity.ok(response); // ⚠️ ALWAYS 200
        }
    }

}