package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
