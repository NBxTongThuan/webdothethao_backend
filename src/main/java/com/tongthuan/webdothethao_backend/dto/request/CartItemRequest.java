package com.tongthuan.webdothethao_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {

    private String userName;
    private double price;
    private int quantity;
    private String productAttributeId;

}
