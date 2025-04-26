package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.LearnWhat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearnWhatRepository extends JpaRepository<LearnWhat, String> {
    List<LearnWhat> findByCourseId(String courseId);
}
