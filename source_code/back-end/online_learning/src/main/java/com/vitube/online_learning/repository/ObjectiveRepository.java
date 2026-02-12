package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ObjectiveRepository extends JpaRepository<Objective, String> {
    List<Objective> findByCourseId(String courseId);
}
