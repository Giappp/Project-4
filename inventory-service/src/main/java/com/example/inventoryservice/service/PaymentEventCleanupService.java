package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.PaymentStatus;
import com.example.inventoryservice.repo.PaymentEventRepo;
import com.example.inventoryservice.repo.ProcessedOrderRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PaymentEventCleanupService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    PaymentEventRepo paymentEventRepo;
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void cleanUpCancelledPaymentEvents(){
        var cancelEvents = paymentEventRepo.findByStatus(PaymentStatus.CANCEL);
        try {
            paymentEventRepo.deleteAllInBatch(cancelEvents);
        } catch (Exception e) {
            logger.error("Error while cleaning cancelled payment events", e);
        }
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void cleanUpRejectPaymentEvents(){
        var rejectPaymentEvents = paymentEventRepo.findByStatus(PaymentStatus.REJECTED);
        try {
            paymentEventRepo.deleteAllInBatch(rejectPaymentEvents);
        }catch (Exception e){
            logger.error("Error while cleaning reject payments: ", e);
        }
    }

    @Scheduled(cron = "0 0 0 */7 * ?")
    @Transactional
    public void cleanUpApprovePaymentEvents(){
        var approvePaymentEvents = paymentEventRepo.findByStatus(PaymentStatus.APPROVED);
        try {
            paymentEventRepo.deleteAllInBatch(approvePaymentEvents);
        }catch (Exception e){
            logger.error("Error while cleaning approve payments: ", e);
        }
    }
}
