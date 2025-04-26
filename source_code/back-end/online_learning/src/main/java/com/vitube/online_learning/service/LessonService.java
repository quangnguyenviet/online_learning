package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.LessonRequest;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Lesson;

import java.io.IOException;
import java.util.List;

public interface LessonService {
//    LessonResponse createLesson(LessonRequest request) throws IOException;
    LessonResponse createLessonS3(LessonRequest request) throws IOException;
    List<LessonResponse> getLessonOfCourse(String courseId);
    Void deleteLesson(String lessonId);
    Void updateLesson(String lessonId, LessonRequest request) throws IOException;


    LessonResponse lessonToLessonResponse(Lesson lesson);
}
