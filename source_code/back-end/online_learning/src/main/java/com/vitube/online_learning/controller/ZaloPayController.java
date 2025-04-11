package com.vitube.online_learning.controller;

import com.vitube.online_learning.service.ZaloPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/zalopay")
public class ZaloPayController {

    @Autowired
    private ZaloPayService zaloPayService;

    private static final String ZALOPAY_ENDPOINT = "https://sb-openapi.zalopay.vn/v2/create";

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder() throws Exception {
        Map<String, Object> requestData = zaloPayService.createOrder();

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
}
