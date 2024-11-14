package com.example.beskbd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_promotion_product")
public class PromotionProduct extends BaseEntity {
    @ManyToOne
    private Product product;
    @ManyToOne
    private Promotion promotion;
    @Column(name = "percentage")
    private Float percentage;
}
