package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Brand;
import com.tongthuan.webdothethao_backend.repository.BrandRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Optional<Brand> findByProductID(String productId) {
        return brandRepository.findByproductId(productId);
    }
}
