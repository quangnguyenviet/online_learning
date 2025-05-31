package com.vitube.online_learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vitube.online_learning.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findByUsername(String username);
    //    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.username = :username AND r.name = :role")

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role.name = :role")
    User findByUsernameAndRole(@Param("username") String username, @Param("role") String role);
}
