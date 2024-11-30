package com.example.beskbd.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItem extends BaseEntity {
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductAttribute product; // Ensure this matches your usage

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    public double getTotalExcludeTaxe() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity)).doubleValue();
    }

    public double getTotalWithTaxe() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.valueOf(1.2)).doubleValue();
    }
}