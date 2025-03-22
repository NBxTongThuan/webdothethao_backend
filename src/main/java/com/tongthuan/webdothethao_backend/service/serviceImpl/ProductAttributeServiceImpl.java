package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.ProductAttributes;
import com.tongthuan.webdothethao_backend.repository.ProductAttributesRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributesRepository productAttributesRepository;

    @Override
    public List<ProductAttributes> findByProductId(String productId) {
        return productAttributesRepository.findByProductId(productId);
    }
}
