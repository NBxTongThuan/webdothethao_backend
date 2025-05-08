package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.dto.request.ReviewRequest.AddReviewRequest;
import com.tongthuan.webdothethao_backend.dto.request.ReviewRequest.UpdateReviewRequest;
import com.tongthuan.webdothethao_backend.dto.response.ReviewResponse.GetReviewResponse;
import com.tongthuan.webdothethao_backend.dto.response.ReviewResponse.SeeReviewResponse;
import com.tongthuan.webdothethao_backend.entity.Reviews;
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


    @GetMapping("/get-list-by-product-id")
    public ResponseEntity<List<GetReviewResponse>> getListReviewsByProductID(@RequestParam("productId") String productId) {
        if (productId.equalsIgnoreCase(""))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(reviewsService.findByProductId(productId).stream().map(GetReviewResponse::new).toList());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody AddReviewRequest reviewRequest) {
       boolean result = reviewsService.addReviews(reviewRequest);
        if (!result)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/see-review")
    public ResponseEntity<SeeReviewResponse> getReviewByOrderItemId(@RequestParam("orderItemId") String orderItemId)
    {
        if(orderItemId.equalsIgnoreCase(""))
        {
            return ResponseEntity.badRequest().build();
        }
        Reviews review = reviewsService.findByOrderItemId(orderItemId);
        if(review == null)
        {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(new SeeReviewResponse(review));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest)
    {
        boolean result = reviewsService.updateReview(updateReviewRequest);
        if(!result)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }


}
