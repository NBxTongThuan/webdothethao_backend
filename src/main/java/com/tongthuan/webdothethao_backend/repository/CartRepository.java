package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Cart;
import com.tongthuan.webdothethao_backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @Query(value = "SELECT * FROM cart WHERE user_id = :userId", nativeQuery = true)
    Optional<Cart> findByUserId(@Param("userId") String userId);

}
