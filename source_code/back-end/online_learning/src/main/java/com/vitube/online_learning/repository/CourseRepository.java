package com.vitube.online_learning.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vitube.online_learning.entity.Course;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends  JpaRepository<Course, String> {

    //    @EntityGraph(attributePaths = "lessons")
    //    List<Course> findAll();

//    @Query("SELECT c FROM Course c " +
//            "WHERE c.title LIKE %:query% OR c.longDesc LIKE %:query% or c.shortDesc LIKE %:query%")
//    List<Course> findByTitleContaining(String query);

    Page<Course> findByTitleContaining(String title, Pageable pageable);

    Page<Course> findByTitleContainingAndCategoryNameContaining(String title, String categoryName, Pageable pageable);

    Page<Course> findByCategoryNameContaining(String categoryName, Pageable pageable);

    Boolean existsByIdAndInstructorId(String id, String instructorId);

}
