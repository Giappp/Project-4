package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.*;
import com.example.inventoryservice.entities.ProductSize;
import com.example.inventoryservice.exception.AppException;
import com.example.inventoryservice.exception.ErrorCode;
import com.example.inventoryservice.repo.OrderEventRepo;
import com.example.inventoryservice.repo.PaymentEventRepo;
import com.example.inventoryservice.repo.ProcessedOrderRepo;
import com.example.inventoryservice.repo.ProductStockRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class InventoryService {
    Logger logger = LoggerFactory.getLogger(InventoryService.class);
    ProductStockRepo productStockRepo;
    ProcessedOrderRepo processedOrderRepo;
    PaymentEventRepo paymentEventRepo;
    OrderEventRepo orderEventRepo;
    KafkaTemplate<String, Object> kafkaTemplate;
    @KafkaListener(topics = "inventory-orders",groupId = "inventory-group")
    @Transactional
    public void processInventoryEvent(OrderEvent orderEvent){
        if(processedOrderRepo.existsById(orderEvent.getId())){
            logger.warn("Duplicate event detected, skipping: {}",orderEvent.getId());
            return;
        }
        ProcessedInventoryEvent inventoryEvent = ProcessedInventoryEvent
                .builder()
                .id(orderEvent.getId())
                .processedAt(LocalDateTime.now())
                .build();
        try{
            processedOrderRepo.save(inventoryEvent);
            switch (orderEvent.getEventType()){
                case CREATED -> {
                    processInventoryReduction(orderEvent.getOrderItems());
                    sendPaymentEvent(orderEvent,true);
                }
                case CANCELLED -> {
                    processInventoryRestoration(orderEvent.getOrderItems());
                    sendPaymentEvent(orderEvent,false);
                }
                default -> logger.warn("Unhandled inventory event type: {}",orderEvent.getEventType());
            }
        }catch (Exception e){
            logger.error("Error processing inventory event ", e);
        }
    }
    @KafkaListener(topics = "payment-error-topic",groupId = "payment-error-group")
    public void processPaymentCancellation(PaymentEvent paymentEvent){
        logger.info("Payment denied for Order id: {}",paymentEvent.getOrderId());
        var orderEvent = orderEventRepo.findByOrderId(paymentEvent.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.EVENT_NOT_FOUND_EXCEPTION));
        processInventoryRestoration(orderEvent.getOrderItems());
        sendOrderEvent(orderEvent,false);
    }

    private void sendOrderEvent(OrderEvent orderEvent,boolean isOk) {
        orderEvent.setEventType(isOk ? OrderEventType.COMPLETED : OrderEventType.CANCELLED);
        logger.info("Send back notification to order service: {}",orderEvent);
        kafkaTemplate.send("inventory-error-topic", orderEvent);
    }

    private void sendPaymentEvent(OrderEvent orderEvent, boolean isConfirmed) {
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .orderId(orderEvent.getOrderId())
                .username(orderEvent.getUsername())
                .status(isConfirmed ? PaymentStatus.PENDING : PaymentStatus.CANCEL)
                .amount(calculateTotalAmount(orderEvent.getOrderItems()))
                .build();
        paymentEventRepo.save(paymentEvent);
        try{
            logger.info("Payment event sent: {}", paymentEvent);
            kafkaTemplate.send("payment", paymentEvent);
        }catch (Exception e){
            logger.error("Error sending payment event for order: {}: ", orderEvent.getOrderId(),e);
        }
    }

    private BigDecimal calculateTotalAmount(List<CartItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void processInventoryReduction(List<CartItem> orderItems) {
        Map<Long, Integer> sizeIdToQuantity = new HashMap<>();
        for (var order : orderItems) {
            sizeIdToQuantity.merge(order.getSizeId(), order.getQuantity(), Integer::sum);
        }

        // Fetch products from database
        List<ProductSize> products = productStockRepo.findAllById(sizeIdToQuantity.keySet());

        // Detect missing products
        Set<Long> foundIds = products.stream()
                .map(ProductSize::getId)
                .collect(Collectors.toSet());
        Set<Long> missingIds = new HashSet<>(sizeIdToQuantity.keySet());
        missingIds.removeAll(foundIds);

        if (!missingIds.isEmpty()) {
            logger.error("Products not found for size IDs: {}", missingIds);
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // Process stock reduction
        for (var product : products) {
            int requiredQuantity = sizeIdToQuantity.get(product.getId());
            if (product.getStock() < requiredQuantity) {
                logger.error("Stock issue for product: {}, required: {}, available: {}",
                        product.getId(), requiredQuantity, product.getStock());
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }
            product.setStock(product.getStock() - requiredQuantity);
        }

        // Save updated stocks
        productStockRepo.saveAll(products);

        logger.info("Inventory reduced for products: {}", sizeIdToQuantity);
    }


    private void processInventoryRestoration(List<CartItem> orderItems){
        for(var item : orderItems){
            var product = productStockRepo.findById(item.getSizeId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            product.setStock(product.getStock() + item.getQuantity());
            productStockRepo.save(product);

            logger.info("Inventory restored for product: {}. quantity: {}",
                    product.getId(), item.getQuantity());
        }
    }
}
