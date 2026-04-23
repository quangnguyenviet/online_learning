package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Role;
import com.vitube.online_learning.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, RoleEnum> {

}
