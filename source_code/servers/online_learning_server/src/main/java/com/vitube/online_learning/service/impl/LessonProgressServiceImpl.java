package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.LessonProgressDTO;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.entity.Lesson;
import com.vitube.online_learning.entity.LessonProgress;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.repository.LessonProgressRepository;
import com.vitube.online_learning.repository.LessonRepository;
import com.vitube.online_learning.mapper.LessonProgressMapper;
import com.vitube.online_learning.repository.RegisterRepository;
import com.vitube.online_learning.service.LessonProgressService;
import com.vitube.online_learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LessonProgressServiceImpl implements LessonProgressService {
    private final UserService userService;
    private final LessonProgressRepository lessonProgressRepository;
    private final LessonRepository lessonRepository;
    private final RegisterRepository registerRepository;
    private final LessonProgressMapper lessonProgressMapper;

    @Override
    public ApiResponse<?> markLessonCompleted(String lessonId) {
        User user = userService.getCurrentUser();
        String uid = user.getId();
        
        // check if is a student
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        boolean isRegistered = registerRepository.existsByStudentIdAndCourseId(uid, lesson.getCourse().getId());
        if (!isRegistered) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        
        LessonProgress lessonProgress = lessonProgressRepository.findByUserIdAndLessonId(uid, lessonId)
                        .orElseGet(() -> {
                            LessonProgress newLessonProgress = LessonProgress.builder()
                                    .user(user)
                                    .lesson(lesson)
                                    .completed(false)
                                    .watchedTime(0L)
                                    .build();
                            return lessonProgressRepository.save(newLessonProgress);
                        });
        lessonProgress.setCompleted(true);
        lessonProgressRepository.save(lessonProgress);
        return ApiResponse.builder()
                .status(1000)
                .build();
    }

    @Override
    public long countFinishedLessons(String userId, String courseId) {
        return lessonProgressRepository.countByUserIdAndLessonCourseIdAndCompletedTrue(userId, courseId);
    }

    @Override
    public LessonProgressDTO getLessonProgress(String userId, String lessonId) {
        LessonProgress lessonProgress = lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElse(null);
        return lessonProgressMapper.entityToDto(lessonProgress);
    }


}
