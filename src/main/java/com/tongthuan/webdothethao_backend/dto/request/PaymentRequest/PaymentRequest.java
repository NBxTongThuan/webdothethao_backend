package com.tongthuan.webdothethao_backend.dto.request.PaymentRequest;

import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String orderId;
    private PaymentStatus paymentStatus;

}
