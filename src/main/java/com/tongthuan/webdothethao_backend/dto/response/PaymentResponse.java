package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.constantvalue.PaymentMethod;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
import com.tongthuan.webdothethao_backend.entity.Payments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String paymentId;
    private LocalDateTime createdDate;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String orderId;

    public PaymentResponse(Payments payments) {
        this.paymentId = payments.getPaymentId();
        this.createdDate = payments.getCreatedDate();
        this.paymentMethod = payments.getPaymentMethod();
        this.paymentStatus = payments.getStatus();
        this.orderId = payments.getOrder().getOrderId();
    }
}
