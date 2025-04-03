package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.entity.CartItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemReponse {

    private String cartItemId;
    private double price;
    private int quantity;
    private String productAttributeId;
    private String productId;
    private String productName;
    private String description;

    public CartItemReponse(CartItems cartItems) {
        this.cartItemId = cartItems.getCartItemId();
        this.price = cartItems.getPrice();
        this.quantity = cartItems.getQuantity();
        this.productAttributeId = cartItems.getProductAttribute().getProductAttributeId();
        this.productId = cartItems.getProductAttribute().getProduct().getProductId();
        this.productName = cartItems.getProductAttribute().getProduct().getProductName();
        this.description = cartItems.getProductAttribute().getProduct().getDescription();
    }
}
