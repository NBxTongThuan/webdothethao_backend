package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Images;

import java.util.List;
import java.util.Optional;

public interface ImagesService {
    List<Images> findByProductId(String productId);

    Optional<Images> findFirstByProductId(String productId);
}
