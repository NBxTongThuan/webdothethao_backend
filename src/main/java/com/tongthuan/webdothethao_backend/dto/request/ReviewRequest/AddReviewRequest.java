package com.tongthuan.webdothethao_backend.dto.request.ReviewRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewRequest {

    private int rating;
    private String comment;
    private String orderItemId;
    private String productId;
    private String productAttributeId;
    private String userName;

}
