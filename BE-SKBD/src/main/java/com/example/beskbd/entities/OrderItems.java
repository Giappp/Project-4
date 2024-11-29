package com.example.beskbd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order_items")
public class OrderItems extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_size_id", nullable = false)
    private ProductSize product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
}
