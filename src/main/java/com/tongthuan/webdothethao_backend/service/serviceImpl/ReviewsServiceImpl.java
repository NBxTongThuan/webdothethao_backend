package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.dto.request.ReviewRequest.AddReviewRequest;
import com.tongthuan.webdothethao_backend.dto.request.ReviewRequest.UpdateReviewRequest;
import com.tongthuan.webdothethao_backend.entity.*;
import com.tongthuan.webdothethao_backend.repository.*;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductAttributesRepository productAttributesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Reviews> findByProductId(String productId) {
        return reviewsRepository.findByProductId(productId);
    }

    @Override
    public boolean addReviews(AddReviewRequest reviewRequest) {

        Reviews review = reviewsRepository.findByOrderItemId(reviewRequest.getOrderItemId());
        if (review != null)
            return false;
        review = new Reviews();
        Products product = productsRepository.findByProductId(reviewRequest.getProductId());
        if (product == null) {
            return false;
        }
        review.setProduct(product);
        ProductAttributes productAttribute = productAttributesRepository.findByProductAttributeId(reviewRequest.getProductAttributeId());
        if (productAttribute == null) {
            return false;
        }
        review.setProductAttribute(productAttribute);
        OrderItems orderItem = orderItemRepository.findByOrderItemId(reviewRequest.getOrderItemId());
        if (orderItem == null) {
            return false;
        }

        review.setOrderItem(orderItem);
        Users user = usersRepository.findByUserName(reviewRequest.getUserName());

        if (user == null) {
            return false;
        }
        review.setUser(user);
        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());
        review.setEdited(false);
        review.setCreatedDate(new Date(System.currentTimeMillis()));
        reviewsRepository.saveAndFlush(review);
        orderItem.setReviewed(true);
        orderItemRepository.saveAndFlush(orderItem);
        return true;
    }

    @Override
    public Reviews findByOrderItemId(String orderItemId) {
        return reviewsRepository.findByOrderItemId(orderItemId);
    }

    @Override
    public boolean updateReview(UpdateReviewRequest updateReviewRequest) {

        Reviews review = reviewsRepository.findById(updateReviewRequest.getReviewId()).orElse(null);
        if(review == null)
        {
            return  false;
        }

        review.setEdited(true);
        review.setRating(updateReviewRequest.getRating());
        review.setComment(updateReviewRequest.getComment());
        return reviewsRepository.saveAndFlush(review) != null;
    }

}
