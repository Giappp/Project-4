package com.example.beskbd.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "created_at")
    protected LocalDateTime createdDate;
    @Column(name = "updated_at")
    protected LocalDateTime updatedDate;

    public BaseEntity(Long id, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.createdDate = createDate;
        this.updatedDate = updateDate;
    }

    public BaseEntity() {

    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
