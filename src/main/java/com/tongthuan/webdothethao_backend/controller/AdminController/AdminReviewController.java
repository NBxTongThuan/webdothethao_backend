package com.tongthuan.webdothethao_backend.controller.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.response.ReviewResponse.SeeReviewResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ReviewsService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private PagedResourcesAssembler<SeeReviewResponse> seeReviewResponsePagedResourcesAssembler;

    @GetMapping("/get-page")
    public ResponseEntity<PagedModel<EntityModel<SeeReviewResponse>>> getAllReviews(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<SeeReviewResponse> seeReviewResponses =
                reviewsService.getAllReview(pageable).map(SeeReviewResponse::new);

        if (seeReviewResponses.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        PagedModel<EntityModel<SeeReviewResponse>> pagedModel =
                seeReviewResponsePagedResourcesAssembler.toModel(seeReviewResponses);

        return ResponseEntity.ok(pagedModel);
    }
}
