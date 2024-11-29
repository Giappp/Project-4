package com.example.beskbd.repositories;

import com.example.beskbd.entities.Product;
import com.example.beskbd.repositories.criteria.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query("SELECT Top(10) from Product p order by p.createdDate desc limit 10")
    List<Product> findTop10ByOrderByCreatedAtDesc();
}
