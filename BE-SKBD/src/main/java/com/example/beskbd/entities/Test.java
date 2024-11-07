package com.example.beskbd.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Test extends BaseEntity {
    private String name;
    private String refreshToken;

}
