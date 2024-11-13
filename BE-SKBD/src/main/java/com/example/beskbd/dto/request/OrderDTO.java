package com.example.beskbd.dto.request;

import com.example.beskbd.dto.response.OrderItemDTO;
import com.example.beskbd.entities.Delivery;
import com.example.beskbd.entities.Oders.OrderEntity;
import com.example.beskbd.entities.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO  {
    private Long orderId;
    private User userId;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime createDate;
    private Double totalAmount;
    private String shippingAddress;
    private double price;
    private Delivery deliveryInfo;
    // Total amount of the order
}