package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest;
import com.tongthuan.webdothethao_backend.entity.CartItems;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    public ResponseEntity<?> addItemToCart(CartItemRequest cartItemRequest);

    public ResponseEntity<?> getCartByUserName(String userName);

    public List<CartItems> getListCartItem(String cartId);


}
