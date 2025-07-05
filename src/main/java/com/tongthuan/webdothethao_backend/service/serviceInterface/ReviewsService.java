package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tongthuan.webdothethao_backend.dto.request.ReviewRequest.AddReviewRequest;
import com.tongthuan.webdothethao_backend.dto.request.ReviewRequest.UpdateReviewRequest;
import com.tongthuan.webdothethao_backend.entity.Reviews;

public interface ReviewsService {
    List<Reviews> findByProductId(String productId);

    boolean addReviews(AddReviewRequest reviewRequest);

    Reviews findByOrderItemId(String orderItemId);

    boolean updateReview(UpdateReviewRequest updateReviewRequest);

    // Admin

    public Page<Reviews> getAllReview(Pageable pageable);
}
