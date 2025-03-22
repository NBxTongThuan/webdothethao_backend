package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.dto.response.ProductAttributeResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productAttribute")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeService productAttributeService;

    @GetMapping
    public ResponseEntity<List<ProductAttributeResponse>> getProductAttribute(@RequestParam("productId") String productId)
    {
        if(productId.equals(""))
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(productAttributeService.findByProductId(productId).stream().map(ProductAttributeResponse::new).toList());
    }

}
