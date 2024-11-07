package com.example.beskbd.entities.Oders;

import com.example.beskbd.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "customer_id")
    private User userId;

    @OneToMany(mappedBy = "order")  // Adjust this if OrderItems has a reference to OrderEntity
    private List<OrderItems> products = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_info_id")
    private Delivery deliveryInfo;

    @OneToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "total_amount")
    private Double totalAmount;

    public OrderEntity(Long id, LocalDateTime createDate, LocalDateTime updateDate, User userid,
                        List<OrderItems> products, Delivery deliveryInfo, Promotion promotion) {
        super((id), createDate, updateDate);
        this.userId = userid;
        this.products = products;
        this.deliveryInfo = deliveryInfo;
        this.promotion = promotion;
    }


    public double calculateTotal() {
        double total = products.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        return total;
    }




}