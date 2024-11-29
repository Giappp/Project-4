package com.example.inventoryservice.repo;

import com.example.inventoryservice.dto.OrderEvent;
import com.example.inventoryservice.entities.ProcessedEvent;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface ProcessedOrderRepo extends JpaRepository<ProcessedEvent,Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ProcessedEvent p WHERE p.processedAt <= :cutoffDate")
    int deleteProcessedEventsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Lock(LockModeType.OPTIMISTIC)
    boolean existsById(Long id);
}
