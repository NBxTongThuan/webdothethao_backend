package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {

}
