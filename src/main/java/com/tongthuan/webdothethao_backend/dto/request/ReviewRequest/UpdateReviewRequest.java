package com.tongthuan.webdothethao_backend.dto.request.ReviewRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequest {
    private String reviewId;
    private String comment;
    private int rating;



}
