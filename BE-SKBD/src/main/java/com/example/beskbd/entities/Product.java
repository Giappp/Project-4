package com.example.beskbd.entities;


import com.example.beskbd.entities.Oders.OrderItems;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Specify inheritance strategy
@DiscriminatorColumn(name = "category_type", discriminatorType = DiscriminatorType.STRING)
public class Product extends BaseEntity {
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String category;

    @OneToMany(mappedBy = "product")
    private Set<ProductCategory> productCategories;
    @OneToMany
    @JoinColumn(name = "product_id")
    private List<OrderItems> orderItems = new ArrayList<>();

    public Product
            (Long id, LocalDateTime createDate, LocalDateTime updateDate,
             String name, String description, double price, int stockQuantity) {
        super(id, createDate, updateDate);
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Product(String name, String description, double price, LocalDateTime createDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdDate = createDate;
    }

    public void updateStock(int quantity) {
        this.stockQuantity -= quantity;
    }

    public boolean isAvailable() {
        return stockQuantity > 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", category='" + category + '\'' +
                '}';

    }
}
