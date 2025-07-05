package com.tongthuan.webdothethao_backend.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.entity.Images;
import com.tongthuan.webdothethao_backend.repository.ImagesRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ImagesService;

@Service
public class ImageServiceImpl implements ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    @Override
    public List<Images> findByProductId(String productId) {
        return imagesRepository.findByProductId(productId);
    }

    @Override
    public Optional<Images> findFirstByProductId(String productId) {
        return imagesRepository.findFirstImageByProductId(productId);
    }
}
