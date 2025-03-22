package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {
}
