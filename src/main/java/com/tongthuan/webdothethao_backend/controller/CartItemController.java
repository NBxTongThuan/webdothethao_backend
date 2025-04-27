package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.constantvalue.ResponseCode;
import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest.UpdateCartItemRequest;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cartItem")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PutMapping("/updateQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody UpdateCartItemRequest updateCartItemRequest)
    {
        boolean result = cartItemService.updateCartItem(updateCartItemRequest);
        if(!result)
            return ResponseEntity.badRequest().body(Map.of("statusCode", ResponseCode.CART_ITEM_NOT_FOUND,
                    "message","Không tim được vật phẩm!"
                    ));
        return ResponseEntity.ok().body(
                Map.of("statusCode",ResponseCode.SUCCESS,
                        "message","Cập nhật số lượng thành công")

        );
    }



}
