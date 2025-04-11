package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
