package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest.UpdateCartItemRequest;

public interface CartItemService {

    public boolean updateCartItem(UpdateCartItemRequest updateCartItemRequest);

}
