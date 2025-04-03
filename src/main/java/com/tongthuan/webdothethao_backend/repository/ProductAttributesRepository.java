package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Images;
import com.tongthuan.webdothethao_backend.entity.ProductAttributes;
import com.tongthuan.webdothethao_backend.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributesRepository extends JpaRepository<ProductAttributes, String> {

    @Query(value = "SELECT * FROM product_attributes WHERE product_id = :productId", nativeQuery = true)
    List<ProductAttributes> findByProductId(@Param("productId") String productId);

    @Query("SELECT p FROM ProductAttributes p WHERE p.productAttributeId = :productAttributeId")
    ProductAttributes findByProductAttributeId(@Param("productAttributeId") String productAttributeId);

}
