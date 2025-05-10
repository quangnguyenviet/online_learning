package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CourseRepository extends JpaRepository<Course, String> {

//    @EntityGraph(attributePaths = "lessons")
//    List<Course> findAll();

    @Query("SELECT c FROM Course c WHERE c.title LIKE %:query%")
    List<Course> findByTitleContaining(String query);

}
