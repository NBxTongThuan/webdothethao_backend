package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Reviews;

import java.util.List;

public interface ReviewsService {
    List<Reviews> findByProductId(String productId);
}
