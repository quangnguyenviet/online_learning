package com.vitube.online_learning.service;

import com.vitube.online_learning.utils.HMACUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ZaloPayService {

    private static final String APP_ID = "2553";
    private static final String KEY1 = "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL";
    private static final String ENDPOINT = "https://sb-openapi.zalopay.vn/v2/create";
    private static final String CALLBACK_URL = "https://9396-2405-4803-ffab-a100-9537-3278-8781-aa68.ngrok-free.app/online_learning/zalopay/callback";
    private final SecurityContextService securityContextService;

    public Map<String, Object> createOrder(String courseId) throws Exception {
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        String appTransId = new SimpleDateFormat("yyMMdd").format(new Date()) + "_" + randomId;
        long appTime = System.currentTimeMillis();

        // Lấy username từ context
        String appUser = securityContextService.getUser().getId();

        // Tạo embed_data chứa courseId
        Map<String, String> embedMap = new HashMap<>();
        embedMap.put("courseId", courseId);
        JSONObject embedData = new JSONObject(embedMap);  // JSON string

        // Danh sách item (nếu có thể thêm sau), hiện tại để trống
        JSONArray items = new JSONArray();

        // Tạo đơn hàng
        Map<String, Object> order = new HashMap<>();
        order.put("app_id", APP_ID);
        order.put("app_trans_id", appTransId);
        order.put("app_user", appUser);
        order.put("amount", 50000);
        order.put("app_time", appTime);
        order.put("bank_code", "zalopayapp");
        order.put("description", "Spring Boot - Đơn hàng #" + randomId);
        order.put("embed_data", embedData.toString());
        order.put("item", items.toString());
        order.put("callback_url", CALLBACK_URL);

        // Tính MAC
        String data = APP_ID + "|" + appTransId + "|" + appUser + "|50000|" + appTime + "|" +
                embedData.toString() + "|" + items.toString();
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, KEY1, data);
        order.put("mac", mac);

        // Gợi ý debug (bạn có thể log ra để kiểm tra)
        System.out.println("MAC input string: " + data);
        System.out.println("MAC: " + mac);
        System.out.println("Embed data: " + embedData.toString());

        return order;
    }

}
