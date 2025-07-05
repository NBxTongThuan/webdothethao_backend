package com.tongthuan.webdothethao_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tongthuan.webdothethao_backend.entity.OrderItems;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, String> {

    @Query("SELECT odt FROM OrderItems odt WHERE odt.order.orderId = :orderId")
    public List<OrderItems> findByOrderId(@Param("orderId") String orderId);

    @Query("SELECT odt FROM OrderItems odt WHERE odt.orderItemId = :orderItemId")
    public OrderItems findByOrderItemId(@Param("orderItemId") String orderItemId);
}
