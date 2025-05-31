package com.vitube.online_learning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vitube.online_learning.entity.InstructorStatic;

public interface InstructorStatisticRepository extends JpaRepository<InstructorStatic, String> {
    // Custom query methods can be defined here if needed

//    @Query("SELECT i FROM InstructorStatic i WHERE i.instructorId = :instructorId and YEAR(CURRENT_DATE) - i.year = 0")
//    List<InstructorStatic> findByInstructorId(String instructorId);
}
