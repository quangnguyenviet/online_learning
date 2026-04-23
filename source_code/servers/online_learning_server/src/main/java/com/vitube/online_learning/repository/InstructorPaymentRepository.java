package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.InstructorPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface InstructorPaymentRepository extends JpaRepository<InstructorPayment, String> {

    @Query(value = """
    SELECT 
        u.id AS instructorId,
        u.first_name,
        u.last_name,
        u.email,
        u.account_number,
        u.account_name,
        u.bank_name,
        s.month,
        s.year,
        s.total_earnings,
        p.paid_at,
        p.id AS statisticId
    FROM instructor_statistic s
    JOIN user u ON s.instructor_id = u.id
    LEFT JOIN instructor_payment p ON p.statistic_id = s.id
    WHERE s.month = :month AND s.year = :year
""", nativeQuery = true)
    List<Map<String, Object>> getInstructorPaymentInfo(
            @Param("month") int month,
            @Param("year") int year
    );
}
