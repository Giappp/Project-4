package com.example.beskbd.services;

import com.example.beskbd.dto.events.CartItem;
import com.example.beskbd.dto.events.OrderEvent;
import com.example.beskbd.dto.events.OrderEventType;
import com.example.beskbd.dto.object.OrderDto;
import com.example.beskbd.dto.request.CancelOrderRequest;
import com.example.beskbd.dto.request.CheckOrderRequest;
import com.example.beskbd.dto.request.CreateOrderRequest;
import com.example.beskbd.dto.response.CreateOrderResponse;
import com.example.beskbd.dto.response.OrderCancelResponse;
import com.example.beskbd.entities.Order;
import com.example.beskbd.entities.OrderItems;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.*;
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
import java.util.List;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    private static final String INVENTORY_TOPIC = "inventory-orders";
    static Logger logger = LoggerFactory.getLogger(OrderService.class);
    OrderRepository orderRepository;
    UserRepository userRepository;
    ProductAttributeRepository productAttributeRepository;
    ProductSizeRepository productSizeRepository;
    KafkaTemplate<String, Object> kafkaTemplate;
    OrderEventRepo orderEventRepo;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        if (request == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (request.getItems() == null) {
            throw new AppException(ErrorCode.ORDER_ITEM_EMPTY);
        }

        var orderItems = request.getItems()
                .stream()
                .map(this::toOrderItems)
                .toList();

        var totalAmount = calculateTotalAmount(orderItems);

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderItems(orderItems)
                .status(Order.Status.PROCESSING)
                .shippingAddress(request.getAddress())
                .totalAmount(totalAmount)
                .build();

        orderRepository.save(order);

        // Create Kafka events
        sendCreateOrderMessageToInventory(order);

        return CreateOrderResponse.builder()
                .order(new OrderDto(order))
                .build();
    }

    private void sendCreateOrderMessageToInventory(Order order) {
        var orderItems = order.getOrderItems()
                .stream()
                .map(CartItem::new)
                .toList();

        OrderEvent orderEvent = OrderEvent
                .builder()
                .orderId(order.getId())
                .eventType(OrderEventType.CREATED)
                .orderItems(orderItems)
                .status(false)
                .username(order.getUser().getUsername())
                .build();

        // Save event if something send message failed
        orderEventRepo.save(orderEvent);

        // Send events to Kafka topics
        try {
            kafkaTemplate.send(INVENTORY_TOPIC, orderEvent)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("Inventory event sent successfully for order: {}", order.getId());
                            orderEvent.setStatus(true);
                            orderEventRepo.save(orderEvent);
                        } else {
                            logger.error("Failed to send inventory event for order: {}", order.getId(), ex);
                        }
                    });
        } catch (Exception e) {
            logger.error("Error sending Kafka events for order: {}", order.getId(), e);
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    @KafkaListener(topics = "inventory-error-topic", groupId = "payment-error-group")
    public void processPaymentCancellation(OrderEvent orderEvent) {
        logger.info("Payment denied, rejecting order: {}", orderEvent.getOrderId());
        var order = orderRepository.findById(orderEvent.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        switch (orderEvent.getEventType()) {
            case COMPLETED -> {
                order.setStatus(Order.Status.PENDING);
                orderRepository.save(order);
            }
            case CANCELLED -> {
                order.setStatus(Order.Status.REJECTED);
                orderRepository.save(order);
            }
            default -> {
                order.setStatus(Order.Status.PROCESSING);
                orderRepository.save(order);
            }
        }
    }


    private BigDecimal calculateTotalAmount(List<OrderItems> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderItems toOrderItems(CartItem orderItemDTO) {
        var size = productSizeRepository.findById(orderItemDTO.getSizeId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        var product = productAttributeRepository.findByProductSize(size);
        return OrderItems.builder()
                .product(size)
                .price(product.getPrice())
                .quantity(orderItemDTO.getQuantity())
                .build();
    }

    public OrderDto checkOrder(CheckOrderRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return new OrderDto(order);
    }

    public OrderCancelResponse cancelOrder(CancelOrderRequest request) {
        var order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() == Order.Status.CANCELLED) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        order.setStatus(Order.Status.CANCELLED);
        orderRepository.save(order);

        sendCancelOrderToInventory(order);

        return OrderCancelResponse.builder()
                .status(true)
                .message("Cancellation successfully")
                .build();
    }

    public void sendCancelOrderToInventory(Order order) {
        if (order.getOrderItems() == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        var orderItems = order.getOrderItems()
                .stream()
                .map(CartItem::new)
                .toList();

        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(order.getId())
                .eventType(OrderEventType.CANCELLED)
                .status(false)
                .username(order.getUser().getUsername())
                .orderItems(orderItems)
                .build();

        // Save event message if send message fail
        orderEventRepo.save(orderEvent);

        try {
            kafkaTemplate.send(INVENTORY_TOPIC, orderEvent)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("Send order cancellation successfully to inventory service: {}"
                                    , order.getId());
                            orderEvent.setStatus(true);
                            orderEventRepo.save(orderEvent);
                        } else {
                            logger.error("Failed to send inventory event for order: {}", order.getId(), ex);
                        }
                    });
        } catch (Exception e) {
            logger.error("Error sending Kafka events for order: {}", order.getId(), e);
        }
    }
}
