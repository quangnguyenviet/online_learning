package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, String> {
}
