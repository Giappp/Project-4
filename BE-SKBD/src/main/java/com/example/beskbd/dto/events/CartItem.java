package com.example.beskbd.dto.events;

import com.example.beskbd.entities.OrderItems;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sizeId;
    private Integer quantity;
    private BigDecimal price;
    private String productName;

    public CartItem(OrderItems orderItems) {
        if (orderItems != null) {
            sizeId = orderItems.getProduct().getId();
            quantity = orderItems.getQuantity();
            price = orderItems.getPrice();
        }
    }
}
