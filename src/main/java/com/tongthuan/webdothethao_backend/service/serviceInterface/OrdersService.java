package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.CancelOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.OrderRequest;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.CountOrderStatusResponse;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.InterestByDateResponse;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.RevenueByDateResponse;
import com.tongthuan.webdothethao_backend.entity.Cart;
import com.tongthuan.webdothethao_backend.entity.Orders;
import com.tongthuan.webdothethao_backend.entity.Payments;
import com.tongthuan.webdothethao_backend.entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrdersService {

//    forUser

    public Orders addCodOrder(OrderRequest orderRequest,HttpServletRequest request);

    public Page<Orders> findByUserName(String userName, Pageable pageable);

    public Page<Orders> findByUserNameAndOrderStatus(String userName, OrderStatus orderStatus, Pageable pageable);

    public Optional<Orders> findByOrderId(String orderId);

    public boolean cancelingOrder(CancelOrderRequest cancelOrderRequest);

    public Orders createVNPayOrder(OrderRequest orderRequest, Users user, Cart cart, String vnpTxnRef);

    public void handleCancelVNPayOrder(Payments payment);

//    forAdmin
    public Page<Orders> adminGetAllOrders(Pageable pageable);

    public boolean adminUpdateOrderByOrderId(AdminUpdateOrderRequest adminUpdateOrderRequest);

    public Page<Orders> adminGetAllOrdersByStatus(Pageable pageable,OrderStatus orderStatus);

    Long getTotalToDayOrder();

    Long getRevenueOfMonth();

    List<RevenueByDateResponse> getRevenueByDateResponse(LocalDate start, LocalDate end);

    List<InterestByDateResponse> getInterestByDateResponse(LocalDate start, LocalDate end);

    Page<Orders> getNewOrders(Pageable pageable);

    CountOrderStatusResponse getOrderStatusRate();

}
