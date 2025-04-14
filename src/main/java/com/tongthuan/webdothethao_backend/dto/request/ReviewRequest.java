package com.tongthuan.webdothethao_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private String reviewId;
    private double rating;
    private String comment;
    private Date createdDate;

}
