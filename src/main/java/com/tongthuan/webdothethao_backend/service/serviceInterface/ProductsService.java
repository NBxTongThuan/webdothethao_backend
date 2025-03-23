package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ProductsService {
    public List<Products> findAll();

    public Page<Products> getAllProducts(Pageable pageable);

    public Optional<Products> getByProductId(String productId);

}
