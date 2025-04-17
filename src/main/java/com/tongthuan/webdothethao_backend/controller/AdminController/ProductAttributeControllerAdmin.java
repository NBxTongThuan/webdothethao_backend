package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.ProductAttributeResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/productAttribute")
public class ProductAttributeControllerAdmin {

    @Autowired
    private ProductAttributeService productAttributeService;

    @GetMapping("/getProductAttributeByProductId")
    public ResponseEntity<List<ProductAttributeResponse>> getProductAttribute(@RequestParam("productId") String productId)
    {
        if(productId.equalsIgnoreCase(""))
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(productAttributeService.findByProductId(productId).stream().map(ProductAttributeResponse::new).toList());
    }




}
