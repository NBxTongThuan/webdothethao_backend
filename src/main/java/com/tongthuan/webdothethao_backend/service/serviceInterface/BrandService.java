package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Brand;

import java.util.Optional;

public interface BrandService {
    public Optional<Brand> findByProductID(String productId);
}
