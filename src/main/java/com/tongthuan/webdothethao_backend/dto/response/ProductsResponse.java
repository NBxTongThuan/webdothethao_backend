package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.entity.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse {

    private String productId;
    private String productName;
    private String description;
    private long moneyOff;
    private int quantitySold;
    private double price;

    public ProductsResponse(Products products) {
        this.productId = products.getProductId();
        this.productName = products.getProductName();
        this.description = products.getDescription();
        this.moneyOff = products.getMoneyOff();
        this.quantitySold = products.getQuantitySold();
        this.price = products.getPrice();
    }
}
