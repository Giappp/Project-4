package com.example.beskbd.repositories.criteria;

import com.example.beskbd.dto.object.ProductFilterDto;
import com.example.beskbd.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<Product> searchProducts(ProductFilterDto filter, Pageable pageable);
}
