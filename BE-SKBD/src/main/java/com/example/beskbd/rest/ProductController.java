package com.example.beskbd.rest;

import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.entities.Product;
import com.example.beskbd.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        product.setCreatedDate(LocalDateTime.now()); // Set the creation date
        productService.createProduct(product.getName(), product.getDescription(), product.getPrice(), product.getCreatedDate());
        return ResponseEntity.ok(ApiResponse.<Product>builder()
                .data(product)
                .message("Product created successfully")
                .success(true)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        productService.updateProduct(id, product.getName(), product.getDescription(), product.getPrice(), LocalDateTime.now());
        Product updatedProduct = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.<Product>builder()
                .data(updatedProduct)
                .message("Product updated successfully")
                .success(true)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .message("Product deleted successfully")
                .success(true)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(ApiResponse.<Product>builder()
                    .data(product)
                    .message("Product retrieved successfully")
                    .success(true)
                    .build());
        } else {
            return ResponseEntity.status(404).body(ApiResponse.<Product>builder()
                    .message("Product not found")
                    .success(false)
                    .build());
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<Product>>> getAllProducts() {
        Iterable<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.<Iterable<Product>>builder()
                .data(products)
                .message("Products retrieved successfully")
                .success(true)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Iterable<Product>>> searchProducts(
            @RequestParam Optional<Double> price,
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> description,
            @RequestParam Optional<Double> minPrice,
            @RequestParam Optional<Double> maxPrice) {

        Iterable<Product> products;

        if (category.isPresent() && price.isPresent() && name.isPresent() && description.isPresent()) {
            products = productService.getProductsByCategoryAndNameAndDescriptionAndCreateDate(
                    category.get(), name.get(), description.get(), LocalDateTime.now()); // Adjust as needed
        } else if (category.isPresent() && price.isPresent()) {
            products = productService.getProductsByCategoryAndPrice(category.get(), price.get());
        } else if (category.isPresent()) {
            products = productService.getProductsByCategory(category.get());
        } else if (price.isPresent()) {
            products = productService.getProductsByPrice(price.get());
        } else if (minPrice.isPresent() && maxPrice.isPresent()) {
            products = productService.getProductsByPriceRange(minPrice.get(), maxPrice.get());
        } else {
            products = productService.getAllProducts();
        }

        return ResponseEntity.ok(ApiResponse.<Iterable<Product>>builder()
                .data(products)
                .message("Products retrieved successfully")
                .success(true)
                .build());
    }
}