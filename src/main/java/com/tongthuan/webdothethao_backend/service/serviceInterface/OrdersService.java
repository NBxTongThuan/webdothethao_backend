package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.CancelOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.OrderRequest;
import com.tongthuan.webdothethao_backend.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrdersService {

//    forUser

    public Orders addCodOrder(OrderRequest orderRequest);

    public Page<Orders> findByUserName(String userName, Pageable pageable);

    public Page<Orders> findByUserNameAndOrderStatus(String userName, OrderStatus orderStatus, Pageable pageable);

    public Optional<Orders> findByOrderId(String orderId);

    public boolean cancelingOrder(CancelOrderRequest cancelOrderRequest);


//    forAdmin
    public Page<Orders> adminGetAllOrders(Pageable pageable);

    public boolean adminUpdateOrderByOrderId(AdminUpdateOrderRequest adminUpdateOrderRequest);

    public Page<Orders> adminGetAllOrdersByStatus(Pageable pageable,OrderStatus orderStatus);
}
