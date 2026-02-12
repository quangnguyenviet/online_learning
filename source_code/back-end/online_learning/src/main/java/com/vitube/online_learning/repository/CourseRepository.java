package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    long countCourseByInstructorId(String instructorId);

    long countCourseByInstructorIdAndPublished(String instructorId, Boolean published);

    @Query("SELECT COUNT(r) FROM Register r JOIN r.course c WHERE c.instructor.id = :instructorId")
    long countTotalStudentsByInstructorId(@Param("instructorId") String instructorId);

    @Query("SELECT COUNT(l) FROM Lesson l JOIN l.course c WHERE c.instructor.id = :instructorId")
    long countTotalVideosByInstructorId(@Param("instructorId") String instructorId);
}
