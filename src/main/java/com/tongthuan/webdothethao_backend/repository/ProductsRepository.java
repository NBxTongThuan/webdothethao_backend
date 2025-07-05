package com.tongthuan.webdothethao_backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tongthuan.webdothethao_backend.entity.Products;
import com.tongthuan.webdothethao_backend.entity.Types;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {

    @Query("SELECT p FROM Products p " + "JOIN p.type t "
            + "JOIN t.categories c "
            + "WHERE c.categoriesId = :categoriesId")
    Page<Products> findProductsByCategoryId(@Param("categoriesId") int categoriesId, Pageable pageable);

    @Query("SELECT p FROM Products p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))")
    Page<Products> findProductsByProductName(@Param("productName") String productName, Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.productId = :productId")
    Products findByProductId(@Param("productId") String productId);

    @Query(
            "SELECT p FROM Products p WHERE p.productName = :productName AND p.type.typename = :typeName AND p.brand.brandName = :brandName")
    Optional<Products> findProductsByProductNameAndTypeName(
            @Param("productName") String productName,
            @Param("typeName") String typeName,
            @Param("brandName") String brandName);

    @Query("SELECT COUNT(p) FROM Products p WHERE p.isInStock = true")
    Long countProductInStock();

    @Query("SELECT p FROM Products p ORDER BY p.quantitySold DESC")
    Page<Products> findTop4BestSellingProducts(Pageable pageable);

    @Query("SELECT p FROM Products p ORDER BY p.createdDate DESC")
    Page<Products> findTopNewestProduct(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.type = :type AND p.productId != :productId")
    Page<Products> findSameTypeProducts(Pageable pageable, String productId, Types type);

    @Query("SELECT p FROM Products p WHERE p.moneyOff > 0")
    Page<Products> findDiscountingProduct(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.isInStock = true AND p.moneyOff = 0")
    Page<Products> findInStockProducts(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.quantitySold > 0 AND p.isInStock = true ORDER BY p.quantitySold DESC")
    Page<Products> findTopSale(Pageable pageable);

    @Query("SELECT p FROM Products p WHERE p.isInStock = true ORDER BY p.quantitySold ASC, p.createdDate ASC")
    Page<Products> findTopSlowSale(Pageable pageable);

    @Query(
            """
			SELECT p FROM Products p
			JOIN p.type t
			JOIN t.categories c
			WHERE c.categoriesId = :categoriesId AND p.price <= :price
			ORDER BY p.price DESC
			""")
    Page<Products> findByCategoryAndPrice(
            Pageable pageable, @Param("categoriesId") int categoryId, @Param("price") long price);

    @Query("""
			SELECT p FROM Products p
			WHERE p.price <= :price
			ORDER BY p.price DESC
			""")
    Page<Products> findByPrice(Pageable pageable, @Param("price") long price);
}
