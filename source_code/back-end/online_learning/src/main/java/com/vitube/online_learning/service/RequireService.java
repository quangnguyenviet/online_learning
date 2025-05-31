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


public interface RequireService {

    //
    List<RequireResponse> getAllRequireByCourseId(String courseId);

    RequireResponse saveRequire(SaveRequireRequest request, String courseId);
}
