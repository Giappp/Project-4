package com.example.beskbd.entities;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Promotion extends BaseEntity {
    private String description;
    private double discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public Promotion
            (Long id, LocalDateTime createDate, LocalDateTime updateDate,
             String description, double discountPercentage, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, createDate, updateDate);
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    public double applyDiscount(double price) {
        if (isActive()) {
            return price - (price * discountPercentage / 100);
        }
        return price;
    }

}

