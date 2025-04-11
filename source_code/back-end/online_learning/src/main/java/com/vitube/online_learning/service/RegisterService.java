package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.RegisterRequest;

public interface RegisterService {
    void registerCourse(RegisterRequest request);
    boolean isRegistered(String courseId);
}
