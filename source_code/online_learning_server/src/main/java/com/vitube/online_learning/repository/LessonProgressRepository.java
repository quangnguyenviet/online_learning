package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, String> {
    Optional<LessonProgress> findByUserIdAndLessonId(String userId, String lessonId);

    long countByUserIdAndLessonCourseIdAndCompletedTrue(String userId, String courseId);

}
