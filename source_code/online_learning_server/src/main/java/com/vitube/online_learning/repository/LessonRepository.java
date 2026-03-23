package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Lesson;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    @Modifying
    @Transactional
    @Query("UPDATE Lesson l SET l.status = :status WHERE l.id = :lessonId")
    void updateStatus(@Param("lessonId") String lessonId,
                      @Param("status") String status);

}
