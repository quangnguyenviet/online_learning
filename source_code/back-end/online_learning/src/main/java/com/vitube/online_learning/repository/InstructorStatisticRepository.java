package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.InstructorStatic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstructorStatisticRepository extends JpaRepository<InstructorStatic, String> {
    // Custom query methods can be defined here if needed

    @Query("SELECT i FROM InstructorStatic i WHERE i.instructorId = :instructorId and YEAR(CURRENT_DATE) - i.year = 0")
    List<InstructorStatic> findByInstructorId(String instructorId);
}
