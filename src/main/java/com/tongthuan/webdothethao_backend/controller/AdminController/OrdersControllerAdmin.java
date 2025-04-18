package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.dto.response.OrderResponse;
import com.tongthuan.webdothethao_backend.repository.OrdersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrdersService;
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
@CrossOrigin("*")
@RequestMapping("/api/admin/orders")
public class OrdersControllerAdmin {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private PagedResourcesAssembler<OrderResponse> orderResponsePagedResourcesAssembler;

    @GetMapping("/getAllOrder")
    public ResponseEntity<PagedModel<EntityModel<OrderResponse>>> getAllOrder(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size,@RequestParam("orderStatus") String orderStatus)
    {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<OrderResponse> orderResponses;
        if(orderStatus.equals("all"))
        {
            orderResponses =  ordersService.adminGetAllOrders(pageable).map(OrderResponse::new);
        }else{
            orderResponses = ordersService.adminGetAllOrdersByStatus(pageable, OrderStatus.valueOf(orderStatus)).map(OrderResponse::new);
        }

        PagedModel<EntityModel<OrderResponse>> pagedModel = orderResponsePagedResourcesAssembler.toModel(orderResponses);

        return ResponseEntity.ok(pagedModel);

    }

    @PutMapping("/updateOrder")
    public ResponseEntity<?> updateOrderByOrderId(@RequestBody AdminUpdateOrderRequest adminUpdateOrderRequest) {
        boolean result = ordersService.adminUpdateOrderByOrderId(adminUpdateOrderRequest);
        if (result)
            return ResponseEntity.ok().body("cap nhat don hang thanh cong");
        return ResponseEntity.badRequest().body("gap loi trong qua trinh xu ly!");
    }

}
