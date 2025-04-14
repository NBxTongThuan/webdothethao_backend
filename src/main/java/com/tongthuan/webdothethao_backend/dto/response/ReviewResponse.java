package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.Reviews;
import com.tongthuan.webdothethao_backend.entity.UserDetails;
import com.tongthuan.webdothethao_backend.repository.UserDetailsRepository;
import com.tongthuan.webdothethao_backend.service.serviceImpl.UserDetailServiceImpl;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UserDetailService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private String reviewId;
    private double rating;
    private String comment;
    private Date createdDate;
    private boolean isEdited;
    private String userName;
    private Color color;
    private String size;

    public ReviewResponse(Reviews reviews){
        this.reviewId = reviews.getReviewId();
        this.rating = reviews.getRating();
        this.comment = reviews.getComment();
        this.createdDate = reviews.getCreatedDate();
        this.isEdited = reviews.isEdited();
        this.userName = reviews.getUser().getUserName();
        this.color = reviews.getProductAttribute().getColor();
        this.size = reviews.getProductAttribute().getSize();
    }

}
