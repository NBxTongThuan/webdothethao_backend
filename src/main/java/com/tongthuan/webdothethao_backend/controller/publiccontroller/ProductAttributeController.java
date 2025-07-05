package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.response.ProductAttributeResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/product-attribute")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeService productAttributeService;

    @GetMapping
    public ResponseEntity<List<ProductAttributeResponse>> getProductAttribute(
            @RequestParam("productId") String productId) {
        if (productId.equals("")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok()
                .body(productAttributeService.findByProductId(productId).stream()
                        .map(ProductAttributeResponse::new)
                        .toList());
    }
}
