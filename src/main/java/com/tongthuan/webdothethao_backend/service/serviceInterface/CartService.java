package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest.AddCartItemRequest;
import com.tongthuan.webdothethao_backend.entity.CartItems;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    public ResponseEntity<?> addItemToCart(AddCartItemRequest cartItemRequest);

    public List<CartItems> getListCartItem(HttpServletRequest request);

    public int deleteCartItem(String cartItemId);

    public boolean deleteAllCartItem(String cartId);


}
