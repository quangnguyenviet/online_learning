package com.vitube.online_learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitube.online_learning.entity.LearnWhat;

public interface LearnWhatRepository extends JpaRepository<LearnWhat, String> {
    List<LearnWhat> findByCourseId(String courseId);
}
