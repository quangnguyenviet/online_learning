package com.vitube.online_learning.service;

import com.vitube.online_learning.utils.HMACUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ZaloPayService {

    private static final String APP_ID = "2553";
    private static final String KEY1 = "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL";
    private static final String ENDPOINT = "https://sb-openapi.zalopay.vn/v2/create";

    public Map<String, Object> createOrder() throws Exception {
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        String appTransId = new SimpleDateFormat("yyMMdd").format(new Date()) + "_" + randomId;
        long appTime = System.currentTimeMillis();

        JSONObject embedData = new JSONObject();  // "{}"
        JSONArray items = new JSONArray();        // "[]"

        Map<String, Object> order = new HashMap<>();
        order.put("app_id", APP_ID);
        order.put("app_trans_id", appTransId);
        order.put("app_user", "user123");
        order.put("amount", 50000);
        order.put("app_time", appTime);
        order.put("bank_code", "zalopayapp");
        order.put("description", "Spring Boot - Đơn hàng #" + randomId);
        order.put("embed_data", embedData.toString());
        order.put("item", items.toString());

        // Tính MAC
        String data = APP_ID + "|" + appTransId + "|user123|50000|" + appTime + "|" +
                embedData.toString() + "|" + items.toString();
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, KEY1, data);
        order.put("mac", mac);

        return order;
    }
}
