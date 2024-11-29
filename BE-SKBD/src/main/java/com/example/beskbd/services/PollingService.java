package com.example.beskbd.services;

import com.example.beskbd.dto.events.OrderEvent;
import com.example.beskbd.repositories.CartItemRepo;
import com.example.beskbd.repositories.OrderEventRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PollingService {

    KafkaTemplate<String, Object> kafkaTemplate;
    OrderEventRepo orderEventRepo;
    CartItemRepo cartItemRepo;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedDelay = 10000)
    public void producer() {
        List<OrderEvent> orderEventTypeList = orderEventRepo.findAllByStatus(false);
        for (var order : orderEventTypeList) {
            kafkaTemplate.send("inventory-orders", order)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("Inventory event sent successfully for order: {}", order.getId());
                            order.setStatus(true);
                            orderEventRepo.save(order);
                        } else {
                            logger.error("Failed to send inventory event for order: {}", order.getId(), ex);
                        }
                    });
        }
    }

    @Scheduled(fixedDelay = 100000)
    @Transactional
    public void delete() {
        List<OrderEvent> orderEvents = orderEventRepo.findAllByStatus(true);
        orderEventRepo.deleteAll(orderEvents);
    }
}
