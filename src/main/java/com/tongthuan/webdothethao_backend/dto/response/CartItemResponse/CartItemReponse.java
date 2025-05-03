package com.tongthuan.webdothethao_backend.dto.response.CartItemResponse;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.CartItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemReponse {

    private String cartItemId;
    private long price;
    private long moneyOff;
    private int quantity;
    private String productAttributeId;
    private String productId;
    private String productName;
    private String description;
    private Color color;
    private String size;
    private int remainQuantity;

    public CartItemReponse(CartItems cartItems) {
        this.cartItemId = cartItems.getCartItemId();
        this.price = cartItems.getPrice();
        this.moneyOff = cartItems.getProductAttribute().getProduct().getMoneyOff();
        this.quantity = cartItems.getQuantity();
        this.productAttributeId = cartItems.getProductAttribute().getProductAttributeId();
        this.productId = cartItems.getProductAttribute().getProduct().getProductId();
        this.productName = cartItems.getProductAttribute().getProduct().getProductName();
        this.description = cartItems.getProductAttribute().getProduct().getDescription();
        this.remainQuantity = cartItems.getProductAttribute().getQuantity();
        this.color = cartItems.getProductAttribute().getColor();
        this.size = cartItems.getProductAttribute().getSize();
    }
}
