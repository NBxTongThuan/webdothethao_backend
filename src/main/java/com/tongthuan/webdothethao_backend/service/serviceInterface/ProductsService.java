package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<Products> findAll();

    Page<Products> getAllProducts(Pageable pageable);

    Optional<Products> getByProductId(String productId);

    Page<Products> getListProductsByCategoryId(int categoryId, Pageable pageable);

    Page<Products> getListProductsByProductName(String productName, Pageable pageable);
}
