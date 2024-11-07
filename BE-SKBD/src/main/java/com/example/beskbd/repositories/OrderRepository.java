package com.example.beskbd.repositories;

import com.example.beskbd.entities.Oders.OrderEntity;
import com.example.beskbd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserId(User userId);
}
