package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.entity.Reviews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private String reviewId;
    private double rating;
    private String comment;
    private Date createdDate;

    public ReviewResponse(Reviews reviews) {
        this.reviewId = reviews.getReviewId();
        this.rating = reviews.getRating();
        this.comment = reviews.getComment();
        this.createdDate = reviews.getCreatedDate();
    }
}
