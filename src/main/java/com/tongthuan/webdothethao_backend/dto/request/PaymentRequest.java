package com.tongthuan.webdothethao_backend.dto.request;

import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private String orderId;
    private PaymentStatus paymentStatus;

}
