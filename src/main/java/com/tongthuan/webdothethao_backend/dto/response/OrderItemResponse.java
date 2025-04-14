package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.OrderItems;
import com.tongthuan.webdothethao_backend.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private String orderItemId;
    private double price;
    private int quantity;
    private boolean isReviewed;
    private String orderId;
    private Color color;
    private String size;
    private String productName;
    private String productId;
    private String productAttributeId;

    public OrderItemResponse(OrderItems orderItems) {
        this.orderItemId = orderItems.getOrderItemId();
        this.price = orderItems.getPrice();
        this.quantity = orderItems.getQuantity();
        this.isReviewed = orderItems.isReviewed();
        this.orderId = orderItems.getOrder().getOrderId();
        this.color = orderItems.getProductAttribute().getColor();
        this.size = orderItems.getProductAttribute().getSize();
        this.productName = orderItems.getProductAttribute().getProduct().getProductName();
        this.productId = orderItems.getProductAttribute().getProduct().getProductId();
        this.productAttributeId = orderItems.getProductAttribute().getProductAttributeId();
    }
}
