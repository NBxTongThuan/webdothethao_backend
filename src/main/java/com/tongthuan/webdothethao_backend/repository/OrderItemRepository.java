package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.OrderItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, String> {

    @Query("SELECT odt FROM OrderItems odt WHERE odt.order.orderId = :orderId")
    public List<OrderItems> findByOrderId(@Param("orderId") String orderId);

}
