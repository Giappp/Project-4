package com.example.beskbd.repositories.criteria.Impl;

import com.example.beskbd.dto.object.ProductFilterDto;
import com.example.beskbd.entities.Product;
import com.example.beskbd.entities.ProductAttribute;
import com.example.beskbd.entities.ProductSize;
import com.example.beskbd.repositories.criteria.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> searchProducts(ProductFilterDto filter, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter != null) {
            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(productRoot.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(productRoot.get("price"), filter.getMaxPrice()));
            }

            // Category
            if (filter.getCategoryId() != null) {
                predicates.add(cb.equal(productRoot.get("category").get("id"), filter.getCategoryId()));
            }

            // Product Sizes
            if (filter.getProductSizes() != null && !filter.getProductSizes().isEmpty()) {
                Join<Product, ProductAttribute> attributesJoin = productRoot.join("attributes");
                Join<ProductAttribute, ProductSize> sizesJoin = attributesJoin.join("sizes");
                predicates.add(sizesJoin.get("size").in(filter.getProductSizes()));
            }

            // Product Colors
            if (filter.getProductColors() != null && !filter.getProductColors().isEmpty()) {
                Join<Product, ProductAttribute> attributesJoin = productRoot.join("attributes");
                predicates.add(attributesJoin.get("color").in(filter.getProductColors()));
            }

            // Product Type
            if (filter.getProductType() != null && !filter.getProductType().isBlank()) {
                predicates.add(cb.equal(productRoot.get("type"), filter.getProductType()));
            }
        }

        // Combine predicates
        query.where(predicates.toArray(new Predicate[0]));

        // Order by creation date (or customize as needed)
        query.orderBy(cb.desc(productRoot.get("createdDate")));

        // Fetch results with pagination
        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // Fetch total count for pagination
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(cb.count(countRoot)).where(predicates.toArray(new Predicate[0]));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), pageable, totalCount);
    }
}
