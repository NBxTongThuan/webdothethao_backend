package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.dto.response.ReviewResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewsService reviewsService;


    @GetMapping("/getListReviews")
    public ResponseEntity<List<ReviewResponse>> getListReviewsByProductID(@RequestParam("productId") String productId)
    {
        if(productId.equals(""))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(reviewsService.findByProductId(productId).stream().map(ReviewResponse::new).toList());
    }

}
