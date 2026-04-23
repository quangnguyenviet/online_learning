package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.SaveRequireRequest;
import com.vitube.online_learning.dto.response.RequireResponse;

import java.util.List;

/**
 * Interface cung cấp các phương thức liên quan đến yêu cầu của khóa học.
 */
public interface RequireService {

    /**
     * Lấy danh sách tất cả các yêu cầu của một khóa học theo ID.
     *
     * @param courseId ID của khóa học.
     * @return Danh sách phản hồi yêu cầu của khóa học.
     */
    List<RequireResponse> getAllRequireByCourseId(String courseId);

    /**
     * Lưu yêu cầu mới vào khóa học.
     *
     * @param request Yêu cầu lưu yêu cầu.
     * @param courseId ID của khóa học.
     * @return Đối tượng phản hồi sau khi lưu yêu cầu.
     */
    RequireResponse saveRequire(SaveRequireRequest request, String courseId);
}