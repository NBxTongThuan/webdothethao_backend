package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {

    @Query("SELECT pm FROM Payments pm WHERE pm.order.orderId = :orderId")
    public Optional<Payments> findByOrderId(@Param("orderId") String orderId);

}
