package com.example.inventoryservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProcessedEvent {
    @Id
    @Column(name = "event_id")
    private Long id;
    @Column(name = "processed_at",nullable = false,updatable = false)
    private LocalDateTime processedAt;
}
