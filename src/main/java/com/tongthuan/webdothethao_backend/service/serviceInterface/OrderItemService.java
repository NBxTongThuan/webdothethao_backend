package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.List;

import com.tongthuan.webdothethao_backend.entity.OrderItems;

public interface OrderItemService {
    public List<OrderItems> findByOrderId(String orderId);
}
