package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.Optional;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.entity.Payments;

public interface PaymentService {

    public Optional<Payments> findByOrderId(String orderId);

    public boolean updateCODPayment(AdminUpdateOrderRequest adminUpdateOrderRequest);
}
