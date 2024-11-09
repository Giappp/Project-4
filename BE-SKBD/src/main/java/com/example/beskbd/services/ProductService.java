package com.example.beskbd.services;

import com.example.beskbd.entities.Product;
import com.example.beskbd.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createProduct(String name, String description, double price, LocalDateTime CreateDate) {
        productRepository.save(new Product(name, description, price, CreateDate));
    }

    public void updateProduct(Long id, String name, String description, double price, LocalDateTime updateDate) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setUpdatedDate(updateDate);
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Iterable<Product> getProductsByPrice(double price) {
        return productRepository.findByPrice(price);
    }

    public Iterable<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public Iterable<Product> getProductsByPriceAndName(double price, String name) {
        return productRepository.findByPriceAndName(price, name);
    }

    public Iterable<Product> getProductsByPriceAndNameAndDescription(double price, String name, String description) {
        return productRepository.findByPriceAndNameAndDescription(price, name, description);
    }

    public Iterable<Product> getProductsByPriceAndNameAndDescriptionAndCreateDate(double price, String name, String description, LocalDateTime createDate) {
        return productRepository.findByPriceAndNameAndDescriptionAndCreatedDate(price, name, description, createDate);
    }

    public Iterable<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Iterable<Product> getProductsByCategoryAndPrice(String category, double price) {
        return productRepository.findByCategoryAndPrice(category, price);
    }

    public Iterable<Product> getProductsByCategoryAndNameAndDescriptionAndCreateDate(String category, String name, String description, LocalDateTime createDate) {
        return productRepository.findByCategoryAndNameAndDescriptionAndCreatedDate(category, name, description, createDate);
    }
}
