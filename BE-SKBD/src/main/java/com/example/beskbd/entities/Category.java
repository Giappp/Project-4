package com.example.beskbd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private Gender gender;
    private String productType;
    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    public enum Gender {
        MEN, WOMEN, UNISEX, CHILD
    }
}