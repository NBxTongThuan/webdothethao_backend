package com.tongthuan.webdothethao_backend.controller.AdminController;

import java.time.LocalDate;
import java.util.List;

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

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.CountOrderStatusResponse;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.InterestByDateResponse;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.RevenueByDateResponse;
import com.tongthuan.webdothethao_backend.dto.response.OrderResponse;
import com.tongthuan.webdothethao_backend.entity.Orders;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrdersService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/orders")
public class AdminOrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private PagedResourcesAssembler<OrderResponse> orderResponsePagedResourcesAssembler;

    @GetMapping("/get-all")
    public ResponseEntity<PagedModel<EntityModel<OrderResponse>>> getAllOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam("orderStatus") String orderStatus) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<OrderResponse> orderResponses;
        if (orderStatus.equals("all")) {
            orderResponses = ordersService.adminGetAllOrders(pageable).map(OrderResponse::new);
        } else {
            orderResponses = ordersService
                    .adminGetAllOrdersByStatus(pageable, OrderStatus.valueOf(orderStatus))
                    .map(OrderResponse::new);
        }

        PagedModel<EntityModel<OrderResponse>> pagedModel =
                orderResponsePagedResourcesAssembler.toModel(orderResponses);

        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOrderByOrderId(@RequestBody AdminUpdateOrderRequest adminUpdateOrderRequest) {
        boolean result = ordersService.adminUpdateOrderByOrderId(adminUpdateOrderRequest);
        if (result) return ResponseEntity.ok().body("cap nhat don hang thanh cong");
        return ResponseEntity.badRequest().body("gap loi trong qua trinh xu ly!");
    }

    @GetMapping("/total-today")
    public ResponseEntity<Long> getTotalOrderToday() {
        return ResponseEntity.ok(ordersService.getTotalToDayOrder());
    }

    @GetMapping("/revenue-of-month")
    public ResponseEntity<Long> getRevenueOfMonth() {
        return ResponseEntity.ok(ordersService.getRevenueOfMonth());
    }

    @GetMapping("/revenue-by-date")
    public ResponseEntity<List<RevenueByDateResponse>> getRevenueByDate(
            @RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        return ResponseEntity.ok(ordersService.getRevenueByDateResponse(start, end));
    }

    @GetMapping("/interest-by-date")
    public ResponseEntity<List<InterestByDateResponse>> getInterestByDate(
            @RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        return ResponseEntity.ok(ordersService.getInterestByDateResponse(start, end));
    }

    @GetMapping("/new")
    public ResponseEntity<PagedModel<EntityModel<OrderResponse>>> getOrderOfMonth(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<OrderResponse> orderResponses =
                ordersService.getNewOrders(pageable).map(OrderResponse::new);

        PagedModel<EntityModel<OrderResponse>> pagedModel =
                orderResponsePagedResourcesAssembler.toModel(orderResponses);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<OrderResponse> getOrderByOrderId(@RequestParam("orderId") String orderId) {
        Orders orders = ordersService.findByOrderId(orderId).orElse(null);
        if (orders == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new OrderResponse(orders));
    }

    @GetMapping("get-rate-by-status")
    public ResponseEntity<CountOrderStatusResponse> getRateOrderByOrderStatus() {
        return ResponseEntity.ok(ordersService.getOrderStatusRate());
    }
}
