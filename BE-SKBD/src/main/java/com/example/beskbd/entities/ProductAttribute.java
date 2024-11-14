package com.example.beskbd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_product_attributes")
public class ProductAttribute extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 50)
    private String color;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer size;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    @OneToMany(mappedBy = "productAttribute")
    private List<ProductImage> productImages;
}
