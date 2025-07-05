package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import com.tongthuan.webdothethao_backend.entity.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductsResponse {

    private String productId;
    private String productName;
    private String description;
    private int quantitySold;
    private long price;
    private long moneyOff;
    private long importPrice;
    private String typeName;
    private String categoryName;
    private boolean isInStock;
    private String brandName;

    public AdminProductsResponse(Products products) {
        this.productId = products.getProductId();
        this.productName = products.getProductName();
        this.description = products.getDescription();
        this.quantitySold = products.getQuantitySold();
        this.price = products.getPrice();
        this.moneyOff = products.getMoneyOff();
        this.importPrice = products.getImportPrice();
        this.typeName = products.getType().getTypename();
        this.categoryName = products.getType().getCategories().getCategoriesName();
        this.isInStock = products.isInStock();
        this.brandName = products.getBrand().getBrandName();
    }
}
