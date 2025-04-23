package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Brand;
import com.tongthuan.webdothethao_backend.repository.BrandRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Optional<Brand> findByProductID(String productId) {
        return brandRepository.findByproductId(productId);
    }

    @Override
    public Page<Brand> getAllBrand(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
