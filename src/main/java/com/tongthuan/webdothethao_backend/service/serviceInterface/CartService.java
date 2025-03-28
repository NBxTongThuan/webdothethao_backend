package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest;
import org.springframework.http.ResponseEntity;

public interface CartService {

    public ResponseEntity<?> addItemToCart(CartItemRequest cartItemRequest);


}
