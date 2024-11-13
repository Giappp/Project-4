package com.example.beskbd.services;

import com.example.beskbd.dto.request.OrderDTO;
import com.example.beskbd.dto.response.OrderItemDTO;
import com.example.beskbd.entities.Delivery;
import com.example.beskbd.entities.Oders.OrderEntity;
import com.example.beskbd.entities.Oders.OrderItems;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.OrderRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService   {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity order = new OrderEntity();
        order.setUserId(orderDTO.getUserId());
        order.setCreatedDate(LocalDateTime.now());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setDeliveryInfo(orderDTO.getDeliveryInfo());

        // Convert OrderItemDTO to OrderItem
        List<OrderItems> orderItems = orderDTO.getOrderItems().stream().map(itemDTO -> {
            OrderItems orderItem = new OrderItems();
            orderItem.setProductName(itemDTO.getProductName());
            orderItem.setPrice(itemDTO.getPrice());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order); // Set the order reference
            return orderItem;
        }).collect(Collectors.toList());

        order.setProducts(orderItems);
        OrderEntity savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDTO).orElse(null);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(OrderEntity order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setCreateDate(order.getCreatedDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setDeliveryInfo(order.getDeliveryInfo());
        // Convert OrderItems to OrderItemDTOs
        List<OrderItemDTO> itemDTOs = order.getProducts().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductName(item.getProductName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setQuantity(item.getQuantity());
            return itemDTO;
        }).collect(Collectors.toList());
        orderDTO.setOrderItems(itemDTOs);
        return orderDTO;
    }

    public OrderDTO updateOrderStatus(Long orderId, Delivery status)throws AppException {
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setDeliveryInfo(status);
            orderRepository.save(order);
            return convertToDTO(order);
        }throw new AppException(ErrorCode.ORDER_NOT_FOUND);
    }
}
