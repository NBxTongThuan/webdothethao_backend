package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {

    @Query("SELECT p FROM Products p JOIN p.type t JOIN t.categories c WHERE c.categoriesId = :categoriesId")
    Page<Products> findProductsByCategoryId(@Param("categoriesId") int categoriesId, Pageable pageable);

    @Query("SELECT p FROM Products p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))")
    Page<Products> findProductsByProductName(@Param("productName") String productName, Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.productId = :productId")
    Products findByProductId(@Param("productId") String productId);

}
