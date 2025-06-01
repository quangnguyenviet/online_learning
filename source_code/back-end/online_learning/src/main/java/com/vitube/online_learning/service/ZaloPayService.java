package com.vitube.online_learning.service;

import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.utils.HMACUtil;

import lombok.RequiredArgsConstructor;

/**
 * Interface cung cấp các phương thức liên quan đến dịch vụ ZaloPay.
 */
public interface ZaloPayService {

    /**
     * Tạo đơn hàng mới cho một khóa học.
     *
     * @param courseId ID của khóa học.
     * @return Thông tin đơn hàng dưới dạng Map.
     * @throws Exception Lỗi xảy ra khi tạo đơn hàng.
     */
    public Map<String, Object> createOrder(String courseId) throws Exception;
}