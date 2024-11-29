package com.example.inventoryservice.repo;

import com.example.inventoryservice.dto.PaymentEvent;
import com.example.inventoryservice.dto.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentEventRepo extends JpaRepository<PaymentEvent, UUID> {
    List<PaymentEvent> findByStatus(PaymentStatus status);
}
