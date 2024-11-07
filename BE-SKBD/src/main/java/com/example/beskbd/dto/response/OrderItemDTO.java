package com.example.beskbd.dto.response;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Long quantity;
    private Double price;
}
