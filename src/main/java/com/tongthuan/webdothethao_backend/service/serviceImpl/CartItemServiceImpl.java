package com.tongthuan.webdothethao_backend.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest.UpdateCartItemRequest;
import com.tongthuan.webdothethao_backend.entity.CartItems;
import com.tongthuan.webdothethao_backend.repository.CartItemsRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Override
    public boolean updateCartItem(UpdateCartItemRequest updateCartItemRequest) {
        CartItems cartItems = cartItemsRepository
                .findById(updateCartItemRequest.getCartItemId())
                .orElse(null);
        if (cartItems == null) return false;

        cartItems.setQuantity(updateCartItemRequest.getQuantity());
        cartItemsRepository.saveAndFlush(cartItems);
        return true;
    }
}
