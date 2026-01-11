package com.vitube.online_learning.repository;

import java.util.List;

import com.vitube.online_learning.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ObjectiveRepository extends JpaRepository<Objective, String> {
    List<Objective> findByCourseId(String courseId);
}
