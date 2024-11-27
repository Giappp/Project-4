package com.example.beskbd.repositories;

import com.example.beskbd.entities.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Long> {
    List<PromotionProduct> findByPromotionId(Long promotionId);
}
