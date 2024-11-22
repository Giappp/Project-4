package com.example.beskbd.services;

import com.example.beskbd.dto.object.ProductAttributeDto;
import com.example.beskbd.dto.request.OrderCreationRequest;
import com.example.beskbd.entities.Order;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void addOrder(OrderCreationRequest request) {
        if (request == null) throw new AppException(ErrorCode.INVALID_REQUEST);

        Order order = new Order();
        order.setUser(request.getUserId());
        order.setDescription(request.getProductDescription());

        var OrderItem = orderRepository.findById(request.getOrderItemsID())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REQUEST));
        order.setOrderItems(OrderItem.getOrderItems());
        order.setShippingAddress(request.getShippingAddress());
        order.setTotalAmount(OrderItem.getTotalAmount());
        order.setStatus(Order.Status.PENDING);
        orderRepository.save(order);

    }

}
