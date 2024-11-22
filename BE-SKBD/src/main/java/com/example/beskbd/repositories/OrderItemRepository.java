package com.example.beskbd.repositories;

import com.example.beskbd.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {
}
