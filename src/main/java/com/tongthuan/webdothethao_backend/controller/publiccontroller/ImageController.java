package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.dto.response.ImageResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImagesService imagesService;

    @GetMapping("/get-all-by-product-id")
    public ResponseEntity<List<ImageResponse>> getAllByProductId(@RequestParam("productId") String productId)
    {
        if(productId.equals(""))
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(imagesService.findByProductId(productId).stream().map(ImageResponse::new).toList());
    }

    @GetMapping("/first-image-of-product")
    public ResponseEntity<Optional<ImageResponse>> getFirstImages(@RequestParam("productId") String productId){
        if(productId.equals(""))
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(imagesService.findFirstByProductId(productId).map(ImageResponse::new));
    }


}
