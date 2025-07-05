package com.tongthuan.webdothethao_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tongthuan.webdothethao_backend.entity.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {

    @Query("SELECT pm FROM Payments pm WHERE pm.order.orderId = :orderId")
    public Optional<Payments> findByOrderId(@Param("orderId") String orderId);

    @Query("SELECT pm FROM Payments pm WHERE pm.vnpTxnRef = :vnpTxnRef")
    public Optional<Payments> findByVnpTxnRef(@Param("vnpTxnRef") String vnpTxnRef);
}
