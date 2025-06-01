package com.vitube.online_learning.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Lớp tiện ích để xử lý mã hóa HMAC.
 */
public class HMACUtil {
    // Thuật toán HMAC SHA256
    public static final String HMACSHA256 = "HmacSHA256";

    /**
     * Mã hóa dữ liệu bằng thuật toán HMAC và trả về chuỗi hex.
     *
     * @param algorithm Thuật toán HMAC (ví dụ: HmacSHA256).
     * @param key Khóa bí mật để mã hóa.
     * @param data Dữ liệu cần mã hóa.
     * @return Chuỗi hex của kết quả mã hóa.
     * @throws Exception Lỗi xảy ra khi mã hóa.
     */
    public static String HMacHexStringEncode(String algorithm, String key, String data) throws Exception {
        // Tạo đối tượng Mac với thuật toán được chỉ định
        Mac mac = Mac.getInstance(algorithm);

        // Tạo khóa bí mật từ chuỗi key
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), algorithm);

        // Khởi tạo Mac với khóa bí mật
        mac.init(secretKeySpec);

        // Mã hóa dữ liệu và lấy kết quả dưới dạng mảng byte
        byte[] hmacBytes = mac.doFinal(data.getBytes("UTF-8"));

        // Chuyển đổi mảng byte thành chuỗi hex
        StringBuilder hash = new StringBuilder();
        for (byte b : hmacBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hash.append('0');
            hash.append(hex);
        }
        return hash.toString();
    }
}