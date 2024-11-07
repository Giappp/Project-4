package com.example.beskbd.entities.Oders;

import com.example.beskbd.entities.BaseEntity;
import com.example.beskbd.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "order_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUser extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
}
