package com.tongthuan.webdothethao_backend.dto.request.OrderRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private double price;
    private int quantity;
    private String productAttributeId;
}
