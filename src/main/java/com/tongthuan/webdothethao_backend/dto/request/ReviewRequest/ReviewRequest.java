package com.tongthuan.webdothethao_backend.dto.request.ReviewRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private int rating;
    private String comment;
    private String orderItemId;
    private String productId;
    private String productAttributeId;
    private String userName;

}
