package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.ImageResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/image")
@CrossOrigin("*")
public class AdminImageController {

    @Autowired
    private ImagesService imagesService;

    @GetMapping("/getAllImage")
    public ResponseEntity<List<ImageResponse>> getAllImageByProductId(@RequestParam("productId") String productId)
    {
        if(productId.equalsIgnoreCase(""))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(imagesService.findByProductId(productId).stream().map(ImageResponse::new).toList());
    }


}
