package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.dto.response.BrandResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/getBrandByProductId")
    public ResponseEntity<Optional<BrandResponse>> getBrandByProductId(@RequestParam("productId") String productId)
    {
        if(productId.equals(""))
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(brandService.findByProductID(productId).map(BrandResponse::new));
    }

}
