package com.vitube.online_learning.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitube.online_learning.dto.request.SaveLearnWhatRequest;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.LearnWhat;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.LearnWhatRepository;

import lombok.RequiredArgsConstructor;

/**
 * Interface cung cấp các phương thức liên quan đến nội dung học.
 */
public interface LearnWhatService {

    /**
     * Lưu nội dung học vào khóa học.
     *
     * @param request Yêu cầu lưu nội dung học.
     * @param courseId ID của khóa học.
     * @return Đối tượng phản hồi sau khi lưu nội dung học.
     */
    public Object saveLearnWhat(SaveLearnWhatRequest request, String courseId);
}