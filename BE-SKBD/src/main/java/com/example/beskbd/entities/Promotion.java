package com.example.beskbd.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_promotion")
public class Promotion extends BaseEntity {
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "promotion_id")
    private List<PromotionProduct> promotionProductList = new ArrayList<>();
}

