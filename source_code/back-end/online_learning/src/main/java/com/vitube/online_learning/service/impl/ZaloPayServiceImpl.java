package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.service.SecurityContextService;
import com.vitube.online_learning.service.UserService;
import com.vitube.online_learning.service.ZaloPayService;
import com.vitube.online_learning.utils.HMACUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Lớp triển khai các phương thức liên quan đến ZaloPay.
 */
@Service
@RequiredArgsConstructor
public class ZaloPayServiceImpl implements ZaloPayService {
    private final CourseRepository courseRepository;

    @Value("${zalopay.appId}")
    private int APP_ID;

    @Value("${zalopay.key1}")
    private String KEY1;

    // URL callback khi thanh toán hoàn tất
    private static final String CALLBACK_URL =
            "https://77fb-2405-4803-f586-6ad0-892f-7309-1740-d908.ngrok-free.app/online_learning/zalopay/callback";
    private final UserService userService;

    /**
     * Tạo đơn hàng ZaloPay.
     *
     * @param courseId ID của khóa học.
     * @return Thông tin đơn hàng dưới dạng Map.
     * @throws Exception Lỗi xảy ra khi tạo đơn hàng.
     */
    public Map<String, Object> createOrder(String courseId) throws Exception {
        // Tạo ID giao dịch ngẫu nhiên
        Random rand = new Random();
        int randomId = rand.nextInt(1000000);
        String appTransId = new SimpleDateFormat("yyMMdd").format(new Date()) + "_" + randomId;
        long appTime = System.currentTimeMillis();

        // Lấy username từ ngữ cảnh bảo mật
        String appUser = userService.getCurrentUser().getId();

        // Tạo embed_data chứa thông tin khóa học
        Map<String, String> embedMap = new HashMap<>();
        embedMap.put("courseId", courseId);
        embedMap.put("redirecturl", "http://localhost:3000/my-learning");
        JSONObject embedData = new JSONObject(embedMap); // Chuỗi JSON

        // Danh sách item (hiện tại để trống)
        JSONArray items = new JSONArray();

        // Lấy thông tin khóa học từ cơ sở dữ liệu
        Course course = courseRepository.findById(courseId).get();
        long amount = course.getNewPrice();

        // Tạo thông tin đơn hàng
        Map<String, Object> order = new HashMap<>();
        order.put("app_id", APP_ID);
        order.put("app_trans_id", appTransId);
        order.put("app_user", appUser);
        order.put("amount", amount);
        order.put("app_time", appTime);
        order.put("bank_code", "zalopayapp");
        order.put("description", "Spring Boot - Đơn hàng #" + randomId);
        order.put("embed_data", embedData.toString());
        order.put("item", items.toString());
        order.put("callback_url", CALLBACK_URL);

        // Tính MAC (Message Authentication Code)
        String data = APP_ID + "|" + appTransId + "|" + appUser + "|" + amount + "|" + appTime + "|"
                + embedData.toString() + "|" + items.toString();

        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, KEY1, data);
        order.put("mac", mac);

        // Gợi ý debug (bạn có thể log ra để kiểm tra)
        System.out.println("MAC input string: " + data);
        System.out.println("MAC: " + mac);
        System.out.println("Embed data: " + embedData.toString());

        return order;
    }
}