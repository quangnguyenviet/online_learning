package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.repository.projection.CourseStatsP;
import com.vitube.online_learning.repository.projection.InstructorCourseP;
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

    // New: methods that filter only published courses
    Page<Course> findByTitleContainingAndPublished(String title, Boolean published, Pageable pageable);

    Page<Course> findByTitleContainingAndCategoryNameContainingAndPublished(String title, String categoryName, Boolean published, Pageable pageable);

    Page<Course> findByCategoryNameContainingAndPublished(String categoryName, Boolean published, Pageable pageable);

    Page<Course> findAllByPublished(Boolean published, Pageable pageable);

    Boolean existsByIdAndInstructorId(String id, String instructorId);

    long countCourseByInstructorId(String instructorId);

    long countCourseByInstructorIdAndPublished(String instructorId, Boolean published);

    @Query("SELECT COUNT(r) FROM Register r JOIN r.course c WHERE c.instructor.id = :instructorId")
    long countTotalStudentsByInstructorId(@Param("instructorId") String instructorId);

    @Query("SELECT COUNT(l) FROM Lesson l JOIN l.course c WHERE c.instructor.id = :instructorId")
    long countTotalVideosByInstructorId(@Param("instructorId") String instructorId);

    @Query(value = """
        SELECT 
            c.id AS id,
            c.title AS title,
            c.published AS published,
            COALESCE(SUM(r.price), 0) AS totalEarnings,
            COUNT(r.id) AS totalRegistrations,
            COALESCE(SUM(l.duration), 0) AS totalDurationInSeconds
        FROM courses c
        LEFT JOIN registers r ON r.course_id = c.id
        LEFT JOIN lessons l ON l.course_id = c.id
        WHERE c.instructor_id = :instructorId
          AND c.published = true
        GROUP BY c.id, c.title, c.published
        ORDER BY totalEarnings DESC
    """, nativeQuery = true)
    Page<CourseStatsP> findTopCoursesByInstructor(@Param("instructorId") String instructorId, Pageable pageable);

    // get courses for instructor
    @Query(value = """
        SELECT 
            c.id AS id,
            c.title AS title,
            c.price AS price,
            c.discount AS discount,
            c.image_url AS imageUrl,
            c.published AS published,
            cat.name AS categoryName,
            c.created_at AS createdAt,
            (SELECT COUNT(l.id) FROM lessons l WHERE l.course_id = c.id) AS numberOfLessons,
            (SELECT COALESCE(SUM(l.duration), 0) FROM lessons l WHERE l.course_id = c.id) AS totalDurationInSeconds,
            (SELECT COUNT(r.id) FROM registers r WHERE r.course_id = c.id) AS totalRegistrations,
            (SELECT COALESCE(SUM(r.price), 0) FROM registers r WHERE r.course_id = c.id) AS totalEarnings
        FROM courses c
        LEFT JOIN categories cat ON c.category_id = cat.id
        WHERE c.instructor_id = :instructorId
          AND c.published = true
    """, nativeQuery = true)
    Page<InstructorCourseP> findCoursesByInstructorId(@Param("instructorId") String instructorId, Pageable pageable);

    Page<Course> findByInstructorId(String instructorId, Pageable pageable);
}
