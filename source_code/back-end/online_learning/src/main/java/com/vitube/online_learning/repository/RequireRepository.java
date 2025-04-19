package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Require;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequireRepository extends JpaRepository<Require, String> {
    // Custom query methods can be defined here if needed
    // For example, to find requirements by course ID
    // List<Require> findByCourseId(String courseId);


    List<Require> findByCourseId(String courseId);
}
