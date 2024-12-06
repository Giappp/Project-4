package com.example.beskbd.repositories;

import com.example.beskbd.entities.ProductAttribute;
import com.example.beskbd.entities.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    @Query("SELECT pa FROM ProductAttribute pa JOIN pa.sizes ps WHERE ps = :size")
    ProductAttribute findByProductSize(@Param("size") ProductSize size);

    @Query("SELECT DISTINCT pa.color FROM ProductAttribute pa WHERE pa.productId = :productId")
    List<String> findAllColorsByProductId(@Param("productId") Long productId);

    @Query("SELECT DISTINCT ps.size FROM ProductAttribute pa JOIN pa.sizes ps WHERE pa.productId = :productId")
    List<Integer> findAllSizesByProductId(@Param("productId") Long productId);

    @Query("SELECT DISTINCT pa.color FROM ProductAttribute pa")
    List<String> findAllByColor();

    @Query("SELECT DISTINCT pa.sizes FROM ProductAttribute pa")
    List<ProductSize> findAllBySizes();
}
