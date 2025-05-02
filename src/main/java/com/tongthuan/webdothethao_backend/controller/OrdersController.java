package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.CancelOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.OrderRequest;
import com.tongthuan.webdothethao_backend.dto.response.OrderResponse;
import com.tongthuan.webdothethao_backend.entity.Orders;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private PagedResourcesAssembler<OrderResponse> orderResponsePagedResourcesAssembler;

    @PostMapping("/codOrder")
    public ResponseEntity<?> orderCOD(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        if (ordersService.addCodOrder(orderRequest,request) == null) {
            return ResponseEntity.badRequest().body("Dat hang that bai!");
        }
        return ResponseEntity.ok().body("Dat hang thanh cong");
    }

    @GetMapping("/myOrders")
    public ResponseEntity<PagedModel<EntityModel<OrderResponse>>> getOrdersByUserName(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size, @RequestParam("userName") String userName,
            @RequestParam("orderStatus") String orderStatus
    ) {
        if (userName.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<OrderResponse> orderResponses;

        if(orderStatus.equals("all"))
        {
            orderResponses = ordersService.findByUserName(userName,pageable).map(OrderResponse::new);
        }
        else {
            orderResponses = ordersService.findByUserNameAndOrderStatus(userName, OrderStatus.valueOf(orderStatus),pageable).map(OrderResponse::new);
        }

        PagedModel<EntityModel<OrderResponse>> pagedModel = orderResponsePagedResourcesAssembler.toModel(orderResponses);

        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/getOrderByOrderId")
    public ResponseEntity<OrderResponse> getOrderByOrderId(@RequestParam("orderId") String orderId)
    {
        Orders orders = ordersService.findByOrderId(orderId).orElse(null);
        if(orders == null)
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new OrderResponse(orders));
    }



    @PutMapping("/cancelOrder")
    public ResponseEntity<?> cancelingOrder(@RequestBody CancelOrderRequest cancelOrderRequest)
    {
        if(cancelOrderRequest.getOrderId().equalsIgnoreCase(""))
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ordersService.cancelingOrder(cancelOrderRequest));
    }


}
