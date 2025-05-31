package com.vitube.online_learning.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitube.online_learning.dto.request.SaveLearnWhatRequest;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.LearnWhat;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.LearnWhatRepository;

import lombok.RequiredArgsConstructor;


public interface LearnWhatService {

    public Object saveLearnWhat(SaveLearnWhatRequest request, String courseId);
}
