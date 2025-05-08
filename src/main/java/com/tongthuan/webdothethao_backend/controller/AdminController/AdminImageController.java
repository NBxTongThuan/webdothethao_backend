package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.ImageResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/images")
@CrossOrigin("*")
public class AdminImageController {

    @Autowired
    private ImagesService imagesService;

    @GetMapping("/get-by-product-id")
    public ResponseEntity<List<ImageResponse>> getAllImageByProductId(@RequestParam("productId") String productId)
    {
        if(productId.equalsIgnoreCase(""))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(imagesService.findByProductId(productId).stream().map(ImageResponse::new).toList());
    }

    @GetMapping("/first-image-of-product")
    public ResponseEntity<Optional<ImageResponse>> getFirstImages(@RequestParam("productId") String productId){
        if(productId.equalsIgnoreCase(""))
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(imagesService.findFirstByProductId(productId).map(ImageResponse::new));
    }


}
