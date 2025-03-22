package com.tongthuan.webdothethao_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tongthuan.webdothethao_backend.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse{

    private String productId;
    private String productName;
    private String description;
    private int quantitySold;
    private double price;

    public ProductsResponse(Products products) {
        this.productId = products.getProductId();
        this.productName = products.getProductName();
        this.description = products.getDescription();
        this.quantitySold = products.getQuantitySold();
        this.price = products.getPrice();
    }
}
