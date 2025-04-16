package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, String> {

    @Query(value = "SELECT * FROM reviews WHERE product_id = :productId", nativeQuery = true)
    List<Reviews> findByProductId(@Param("productId") String productId);

    @Query("SELECT rv FROM Reviews rv WHERE rv.orderItem.orderItemId = :orderItemId")
    Reviews findByOrderItemId(@Param("orderItemId") String orderItemId);

}
