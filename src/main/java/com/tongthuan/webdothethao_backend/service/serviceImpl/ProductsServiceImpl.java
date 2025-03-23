package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Products;
import com.tongthuan.webdothethao_backend.repository.ProductsRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Page<Products> getAllProducts(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    @Override
    public Optional<Products> getByProductId(String productId) {
        return productsRepository.findById(productId);
    }
}
