package com.example.beskbd.entities.Oders;


import com.example.beskbd.entities.BaseEntity;
import com.example.beskbd.entities.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class OrderItems extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "product_name")
    @JoinColumn(name = "product_name")
    private String productName;
    @ManyToOne
    @JoinColumn(name = "order_id")  // Foreign key column
    private OrderEntity order;
    @Column(name = "quantity")
    @JoinColumn(name = "quantity")
    private Long quantity;
    @Column(name = "price")
    @JoinColumn(name = "price")
    private double price;


    public OrderItems(Long id, LocalDateTime createDate, LocalDateTime updateDate, Product product, Long quantity) {
        super((id), createDate, updateDate);
        if (quantity <= 0) {

            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        setId(id);
        setCreatedDate(createDate);
        setUpdatedDate(updateDate);
        this.product = product;
        this.quantity = quantity;
    }


}