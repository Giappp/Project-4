package com.example.beskbd.dto.object;

import com.example.beskbd.dto.events.CartItem;
import com.example.beskbd.entities.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private String userName;
    private Long orderId;
    private String shippingAddress;
    private List<CartItem> orderItems;
    private String orderStatus;
    private LocalDateTime createdDate;

    public OrderDto(Order order) {
        if (order != null) {
            orderId = order.getId();
            shippingAddress = order.getShippingAddress();
            orderStatus = order.getStatus().toString();
            createdDate = order.getCreatedDate();
            userName = order.getUser().getUsername();
            if (order.getOrderItems() != null) {
                orderItems = order.getOrderItems()
                        .stream()
                        .map(CartItem::new)
                        .toList();
            }
        }
    }
}
