package com.example.inventoryservice.repo;

import com.example.inventoryservice.dto.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderEventRepo extends JpaRepository<OrderEvent,Long> {
    Optional<OrderEvent> findByOrderId(Long orderId);
}
