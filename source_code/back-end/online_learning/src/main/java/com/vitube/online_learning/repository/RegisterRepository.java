package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.repository.projection.RegistrationStatsP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    FROM registers r
    JOIN courses c ON r.course_id = c.id
    WHERE c.instructor_id = :instructorId
      AND r.register_date >= CURRENT_DATE - INTERVAL '6 days'
      AND r.register_date <= CURRENT_DATE
    GROUP BY DATE(r.register_date)
    ORDER BY registrationDay DESC
""", nativeQuery = true)
    List<RegistrationStatsP> countRegistrationsStatsByDay(
            @Param("instructorId") String instructorId
    );
    
    
    @Query(value = """
    SELECT 
        date_trunc('week', r.register_date)::date AS registrationDay,
        COUNT(r.id) AS totalRegistrations
    FROM registers r
    JOIN courses c ON r.course_id = c.id
    WHERE c.instructor_id = :instructorId
      AND r.register_date >= CURRENT_DATE - INTERVAL '12 weeks'
    GROUP BY date_trunc('week', r.register_date)
    ORDER BY registrationDay DESC
""", nativeQuery = true)
    List<RegistrationStatsP> countRegistrationsStatsByWeek(
            @Param("instructorId") String instructorId
    );
    
    
    @Query(value = """
    SELECT 
        (date_trunc('month', r.register_date) + INTERVAL '1 month - 1 day')::date AS registrationDay,
        COUNT(r.id) AS totalRegistrations
    FROM registers r
    JOIN courses c ON r.course_id = c.id
    WHERE c.instructor_id = :instructorId
      AND r.register_date >= CURRENT_DATE - INTERVAL '12 months'
    GROUP BY date_trunc('month', r.register_date)
    ORDER BY registrationDay DESC
""", nativeQuery = true)
    List<RegistrationStatsP> countRegistrationsStatsByMonth(
            @Param("instructorId") String instructorId
    );

    Page<Register> findByStudentId(String userId, Pageable pageable);

}
