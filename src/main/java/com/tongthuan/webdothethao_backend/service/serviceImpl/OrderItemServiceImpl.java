package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.OrderItems;
import com.tongthuan.webdothethao_backend.repository.OrderItemRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItems> findByOrderId(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
