package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.List;
import java.util.Optional;

import com.tongthuan.webdothethao_backend.entity.Images;

public interface ImagesService {

    List<Images> findByProductId(String productId);

    Optional<Images> findFirstByProductId(String productId);
}
