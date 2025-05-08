package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest.AddCartItemRequest;
import com.tongthuan.webdothethao_backend.dto.response.CartItemResponse.CartItemReponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(@RequestBody AddCartItemRequest cartItemRequest) {
        return cartService.addItemToCart(cartItemRequest);

    }

    @GetMapping("/get-list")
    public ResponseEntity<List<CartItemReponse>> getListCartItem(@RequestParam("cartId") String cartId) {
        if (cartId.equals("")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(cartService.getListCartItem(cartId).stream().map(CartItemReponse::new).toList());
    }

    @DeleteMapping("/delete-cart-item")
    public ResponseEntity<String> deleteCartItemByID(@RequestParam("cartItemID") String cartItemID) {
        if (cartService.deleteCartItem(cartItemID) != 0) {
            return ResponseEntity.ok().body("true");
        }
        return ResponseEntity.badRequest().body("false");
    }


}
