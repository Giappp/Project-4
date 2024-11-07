
package com.example.beskbd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_category")
@Getter
@Setter
public class ProductCategory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    // Getters and Setters
}