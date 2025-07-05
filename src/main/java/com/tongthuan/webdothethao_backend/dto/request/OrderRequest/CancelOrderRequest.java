package com.tongthuan.webdothethao_backend.dto.request.OrderRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderRequest {
    private String orderId;
    private String orderCancelNote;
}
