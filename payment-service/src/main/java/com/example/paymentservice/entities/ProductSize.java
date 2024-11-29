package com.example.paymentservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_product_stock_size")
public class ProductSize extends BaseEntity {
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer size;
}
