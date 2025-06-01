package com.vitube.online_learning.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vitube.online_learning.dto.request.SaveRequireRequest;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Require;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.RequireRepository;

import lombok.RequiredArgsConstructor;

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