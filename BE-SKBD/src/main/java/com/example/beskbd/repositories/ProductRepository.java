package com.example.beskbd.repositories;

import com.example.beskbd.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Iterable<Product> findByPrice(double price);

    Iterable<Product> findByPriceBetween(double minPrice, double maxPrice);

    Iterable<Product> findByPriceAndName(double price, String name);

    Iterable<Product> findByPriceAndNameAndDescription(double price, String name, String description);

    Iterable<Product> findByPriceAndNameAndDescriptionAndCreatedDate(double price, String name, String description, LocalDateTime createDate);

    Iterable<Product> findByCategory(String category);

    Iterable<Product> findByCategoryAndPrice(String category, double price);

    Iterable<Product> findByCategoryAndPriceAndName(String category, double price, String name);

    Iterable<Product> findByCategoryAndNameAndDescription(String category, String name, String description);

    Iterable<Product> findByCategoryAndNameAndDescriptionAndCreatedDate( String category, String name, String description, LocalDateTime createdDate);
}
