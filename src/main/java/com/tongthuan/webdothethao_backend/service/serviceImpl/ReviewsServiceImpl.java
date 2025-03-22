package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Reviews;
import com.tongthuan.webdothethao_backend.repository.ReviewsRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Override
    public List<Reviews> findByProductId(String productId) {
        return reviewsRepository.findByProductId(productId);
    }
}
