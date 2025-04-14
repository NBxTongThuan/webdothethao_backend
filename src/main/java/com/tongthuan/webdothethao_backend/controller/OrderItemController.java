package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.response.OrderItemResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orderItems")
public class OrderItemController {

    @Autowired
    private PagedResourcesAssembler<OrderItemResponse> orderItemResponsePagedResourcesAssembler;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/listOrderItem")
    public ResponseEntity<List<OrderItemResponse>> getListOrderItemByOrderId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size, @RequestParam("orderId")String orderId)
    {
        if(orderId.equalsIgnoreCase(""))
        {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(orderItemService.findByOrderId(orderId).stream().map(OrderItemResponse::new).toList());

    }

}
