package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.ProductAttributes;

import java.util.List;

public interface ProductAttributeService {

    public List<ProductAttributes> findByProductId(String productId);

}
