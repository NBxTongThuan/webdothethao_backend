package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.entity.Payments;
import com.tongthuan.webdothethao_backend.repository.PaymentsRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Override
    public Optional<Payments> findByOrderId(String orderId) {
        return paymentsRepository.findByOrderId(orderId);
    }

    @Override
    public boolean updateCODPayment(AdminUpdateOrderRequest adminUpdateOrderRequest) {

        Payments payment = paymentsRepository.findByOrderId(adminUpdateOrderRequest.getOrderId()).orElse(null);

        if (payment == null)
            return false;

        paymentsRepository.saveAndFlush(payment);
        return true;
    }
}
