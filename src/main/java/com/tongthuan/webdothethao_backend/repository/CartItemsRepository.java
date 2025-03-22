package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Cart;
import com.tongthuan.webdothethao_backend.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, String> {

    @Query(value = "SELECT * FROM cart_items WHERE cart_id = :cartId", nativeQuery = true)
    List<CartItems> findByCartId(@Param("cartId") String cartId);

}
