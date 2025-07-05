package com.tongthuan.webdothethao_backend.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.entity.OrderItems;
import com.tongthuan.webdothethao_backend.repository.OrderItemRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItems> findByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
