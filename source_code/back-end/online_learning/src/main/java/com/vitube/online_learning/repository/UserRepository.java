package com.vitube.online_learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vitube.online_learning.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
//    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.username = :username AND r.name = :role")


    @Query(value = "SELECT u.* FROM user u " +
            "JOIN user_roles ur ON u.id = ur.user_id " +
            "JOIN role r ON r.name = ur.roles_name " +
            "WHERE u.username = :username AND r.name = :role;",
            nativeQuery = true)
    User findByUsernameAndRole(@Param("username") String username, @Param("role") String role);


}
