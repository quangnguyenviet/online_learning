package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.repository.projection.RegistrationStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RegisterRepository extends JpaRepository<Register, String> {
    boolean existsByStudentIdAndCourseId(String studentId, String courseId);

    @Query("SELECT SUM(r.price) FROM Register r JOIN r.course c WHERE c.instructor.id = :instructorId")
    BigDecimal sumPriceByInstructorId(@Param("instructorId") String instructorId);

    @Query(value = """
    SELECT 
        DATE(r.register_date) AS registrationDay,
        COUNT(r.id) AS totalRegistrations
    FROM register r
    JOIN course c ON r.course_id = c.id
    WHERE c.instructor_id = :instructorId
      AND r.register_date >= CURDATE() - INTERVAL 6 DAY
      AND r.register_date <= CURDATE()
    GROUP BY DATE(r.register_date)
    ORDER BY registrationDay DESC
""", nativeQuery = true)
    List<RegistrationStats> countRegistrationsStatsByDay(
            @Param("instructorId") String instructorId
    );
    
    
    @Query(value = """
    SELECT 
        STR_TO_DATE(CONCAT(YEARWEEK(r.register_date, 1), ' Monday'), '%X%V %W') AS registrationDay,
        COUNT(r.id) AS totalRegistrations
    FROM register r
    JOIN course c ON r.course_id = c.id
    WHERE c.instructor_id = :instructorId
      AND r.register_date >= CURDATE() - INTERVAL 12 WEEK
    GROUP BY YEARWEEK(r.register_date, 1)
    ORDER BY registrationDay DESC
""", nativeQuery = true)
    List<RegistrationStats> countRegistrationsStatsByWeek(
            @Param("instructorId") String instructorId
    );
    
    
    @Query(value = """
    SELECT 
        LAST_DAY(r.register_date) AS registrationDay,
        COUNT(r.id) AS totalRegistrations
    FROM register r
    JOIN course c ON r.course_id = c.id
    WHERE c.instructor_id = :instructorId
      AND r.register_date >= CURDATE() - INTERVAL 12 MONTH
    GROUP BY LAST_DAY(r.register_date)
    ORDER BY registrationDay DESC
""", nativeQuery = true)
    List<RegistrationStats> countRegistrationsStatsByMonth(
            @Param("instructorId") String instructorId
    );

}
