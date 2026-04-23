package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.LessonProgressDTO;
import com.vitube.online_learning.dto.response.ApiResponse;

public interface LessonProgressService {
    ApiResponse<?> markLessonCompleted(String lessonId);

    long countFinishedLessons(String userId, String courseId);

    LessonProgressDTO getLessonProgress(String userId, String lessonId);
}
