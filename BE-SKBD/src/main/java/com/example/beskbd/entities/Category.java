package com.example.beskbd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String productType;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Set<Product> products;

    public enum Gender {
        MEN, WOMEN, UNISEX, CHILD
    }
}