package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.OrderItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderItemResponse {

    private String orderItemId;
    private long finalPrice;
    private long originalPrice;
    private long importPrice;
    private int quantity;
    private boolean reviewed;
    private String orderId;
    private Color color;
    private String size;
    private long moneyOffPerOneProduct;
    private String productName;
    private String productId;
    private String productAttributeId;

    public AdminOrderItemResponse(OrderItems orderItems) {
        this.orderItemId = orderItems.getOrderItemId();
        this.finalPrice = orderItems.getFinalPrice();
        this.originalPrice = orderItems.getOriginalPrice();
        this.moneyOffPerOneProduct = orderItems.getMoneyOffPerOneProduct();
        this.quantity = orderItems.getQuantity();
        this.reviewed = orderItems.isReviewed();
        this.importPrice = orderItems.getImportPrice();
        this.orderId = orderItems.getOrder().getOrderId();
        this.color = orderItems.getProductAttribute().getColor();
        this.size = orderItems.getProductAttribute().getSize();
        this.productName = orderItems.getProductAttribute().getProduct().getProductName();
        this.productId = orderItems.getProductAttribute().getProduct().getProductId();
        this.productAttributeId = orderItems.getProductAttribute().getProductAttributeId();
    }
}
