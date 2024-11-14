package com.example.beskbd.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "promotion")
    private List<PromotionProduct> promotionProductList;
}

