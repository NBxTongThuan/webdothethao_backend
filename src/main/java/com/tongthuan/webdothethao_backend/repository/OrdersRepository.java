package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {

    @Query("SELECT od FROM Orders od WHERE od.user.userName = :userName AND od.deleted = false")
    Page<Orders> findByUserName(@Param("userName") String userName,Pageable pageable);

    @Query("SELECT od FROM Orders od WHERE od.user.userName = :userName AND od.status = :orderStatus AND od.deleted = false")
    Page<Orders> findByUserNameAndOrderStatus(@Param("userName") String userName, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    @Query("SELECT od FROM Orders od WHERE od.orderId = :orderId AND od.deleted = false")
    Optional<Orders> findByOrderId(@Param("orderId") String orderId);

    @Query("SELECT od FROM Orders od WHERE od.status = :orderStatus AND od.deleted = false")
    Page<Orders> adminFindAllByOrderStatus(@Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

}
