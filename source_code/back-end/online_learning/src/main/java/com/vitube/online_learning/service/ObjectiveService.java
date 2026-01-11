package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.SaveLearnWhatRequest;

/**
 * Interface cung cấp các phương thức liên quan đến nội dung học.
 */
public interface ObjectiveService {

    /**
     * Lưu nội dung học vào khóa học.
     *
     * @param request Yêu cầu lưu nội dung học.
     * @param courseId ID của khóa học.
     * @return Đối tượng phản hồi sau khi lưu nội dung học.
     */
    public Object saveLearnWhat(SaveLearnWhatRequest request, String courseId);
}