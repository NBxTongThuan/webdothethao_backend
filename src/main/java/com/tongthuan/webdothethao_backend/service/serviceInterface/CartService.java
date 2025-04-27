package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest.AddCartItemRequest;
import com.tongthuan.webdothethao_backend.entity.CartItems;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    public ResponseEntity<?> addItemToCart(AddCartItemRequest cartItemRequest);

    public List<CartItems> getListCartItem(String cartId);

    public int deleteCartItem(String cartItemId);




}
