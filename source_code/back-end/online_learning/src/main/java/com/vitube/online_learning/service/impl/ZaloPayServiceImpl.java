package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.zalopay.CreateOrderRequest;
import com.vitube.online_learning.dto.zalopay.CreateOrderResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.service.SecurityContextService;
import com.vitube.online_learning.service.UserService;
import com.vitube.online_learning.service.ZaloPayService;
import com.vitube.online_learning.utils.HMACUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Lớp triển khai các phương thức liên quan đến ZaloPay.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ZaloPayServiceImpl implements ZaloPayService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final WebClient zaloPayWebClient;

    @Value("${zalopay.appId}")
    private int APP_ID;

    @Value("${zalopay.key1}")
    private String KEY1;

    @Value("${zalopay.callbackUrl")
    private String CALLBACK_URL;

    @Value("${zalopay.redirectUrl")
    private String REDIRECT_URL;


    public ApiResponse<?> createOrder(String courseId) throws Exception {
        // get current user
        User currentUser = userService.getCurrentUser();

        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );

        // create items array
        JSONArray items = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("itemid", courseId);
        item.put("itemname", course.getTitle());
        item.put("itemprice", course.getNewPrice());
        items.put(item);

        // create embed data
        JSONObject embedData = new JSONObject();
        embedData.put("courseId", courseId);
        embedData.put("redirecturl", REDIRECT_URL);
        embedData.put("preferred_payment_method", new JSONArray());


        // create order data
        CreateOrderRequest request = CreateOrderRequest.builder()
                .appId(APP_ID)
                .appUser(currentUser.getEmail())
                .appTime(Instant.now().toEpochMilli())
                .amount(course.getNewPrice())
                .appTransId(getCurrentTimeString() + "_" + new Random().nextInt(1000000))
                .item(items.toString())
                .bankCode("")
                .description("Spring Boot - Đơn hàng #" + courseId)
                .embedData(embedData.toString())
                .email(currentUser.getEmail())
                .callbackUrl(CALLBACK_URL)
                .build();

        log.info("ZaloPay form data = {}", request);

        // calculate mac
        String mac = createMac(request);

        request.setMac(mac);

        // using webclient to send request to zalopay
        Mono<CreateOrderResponse> mono = zaloPayWebClient
                .post()
                .uri("/v2/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CreateOrderResponse.class);

        CreateOrderResponse response = mono.block();


        return ApiResponse.builder()
                .status(1000)
                .data(response)
                .build();
    }


    public String getCurrentTimeString(){
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear()).substring(2);
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        return year + month + day;
    }

    public String createMac(CreateOrderRequest request) throws Exception {
        String data = request.getAppId() + "|" +
                request.getAppTransId() + "|" +
                request.getAppUser() + "|" +
                request.getAmount() + "|" +
                request.getAppTime() + "|" +
                request.getEmbedData() + "|" +
                request.getItem();
        return HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, KEY1, data);
    }
}