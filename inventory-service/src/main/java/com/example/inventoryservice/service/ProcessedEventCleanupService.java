package com.example.inventoryservice.service;

import com.example.inventoryservice.repo.ProcessedOrderRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProcessedEventCleanupService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    ProcessedOrderRepo processedOrderRepo;

    @Scheduled(fixedRateString = "${cleanup.processed-events.interval}")
    @Transactional
    public void cleanUpProcessedEvents(){
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);
        try {
            processedOrderRepo.deleteProcessedEventsOlderThan(cutoffDate);
            logger.info("Deleted processed events older than: {}", cutoffDate);
        } catch (Exception e) {
            logger.error("Error while cleaning old processed events", e);
        }
    }
}
