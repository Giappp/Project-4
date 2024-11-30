package com.example.beskbd.dto.events;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;
    private Long orderId;
    private String username;
    private BigDecimal amount;
    private PaymentStatus status;
}
