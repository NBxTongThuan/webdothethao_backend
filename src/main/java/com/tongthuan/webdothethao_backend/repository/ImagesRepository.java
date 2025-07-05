package com.tongthuan.webdothethao_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tongthuan.webdothethao_backend.entity.Images;

@Repository
public interface ImagesRepository extends JpaRepository<Images, String> {

    @Query(value = "SELECT * FROM images WHERE product_id = :productId", nativeQuery = true)
    List<Images> findByProductId(@Param("productId") String productId);

    @Query(value = "SELECT * FROM images WHERE product_id = :productId LIMIT 1", nativeQuery = true)
    Optional<Images> findFirstImageByProductId(@Param("productId") String productId);
}
