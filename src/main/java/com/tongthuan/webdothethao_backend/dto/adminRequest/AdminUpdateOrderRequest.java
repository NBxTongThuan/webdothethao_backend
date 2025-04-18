package com.tongthuan.webdothethao_backend.dto.adminRequest;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateOrderRequest {

    private String orderId;
    private OrderStatus orderStatus;
    private String orderCancelNote;

}
