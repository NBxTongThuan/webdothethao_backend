package com.tongthuan.webdothethao_backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.ProductAttributes;

@Repository
public interface ProductAttributesRepository extends JpaRepository<ProductAttributes, String> {

    @Query("SELECT p FROM ProductAttributes p WHERE p.product.productId = :productId AND p.enable = true")
    List<ProductAttributes> findByProductId(@Param("productId") String productId);

    @Query("SELECT p FROM ProductAttributes p WHERE p.productAttributeId = :productAttributeId")
    ProductAttributes findByProductAttributeId(@Param("productAttributeId") String productAttributeId);

    @Query("SELECT p FROM ProductAttributes p WHERE p.product.productId = :productId")
    Page<ProductAttributes> finAllByProductId(@Param("productId") String productId, Pageable pageable);

    @Query(
            "SELECT pa FROM ProductAttributes pa WHERE pa.product.productId = :productId AND pa.color = :color AND pa.size = :size")
    ProductAttributes findByColorAndSize(
            @Param("productId") String productId, @Param("color") Color color, @Param("size") String size);
}
