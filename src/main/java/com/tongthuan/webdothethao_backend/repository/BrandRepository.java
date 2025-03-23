package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {

    Optional<Brand> findByBrandName(String brandName);

    //Qery này làm việc dựa trên entity
    @Query("SELECT b FROM Brand b JOIN b.productsList p WHERE p.productId = :productId")
    Optional<Brand> findByproductId(@Param("productId") String productId);

}
