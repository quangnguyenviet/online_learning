package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.entity.Register;

public interface RegisterService {
    // convertor
    Register toEntity(RegisterRequest request);
    // convertor


    void createRegisterData(RegisterRequest request);
    boolean isRegistered(String courseId);
}
