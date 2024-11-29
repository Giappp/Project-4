package com.example.beskbd.repositories;

import com.example.beskbd.dto.events.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEventRepo extends JpaRepository<OrderEvent, Long> {
    List<OrderEvent> findAllByStatus(boolean status);
}
