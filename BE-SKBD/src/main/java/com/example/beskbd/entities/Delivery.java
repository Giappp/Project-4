package com.example.beskbd.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery")
public class Delivery extends BaseEntity {

    private String trackingNumber;
    private String carrier;
    private String address;
    private String shippingStatus;

    public void updateStatus(String status) {
        this.shippingStatus = status;
    }

}

