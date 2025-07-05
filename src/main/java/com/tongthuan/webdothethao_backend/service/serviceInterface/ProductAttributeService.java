package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.AddProductAttributeRequest;
import com.tongthuan.webdothethao_backend.entity.ProductAttributes;

public interface ProductAttributeService {

    public List<ProductAttributes> findByProductId(String productId);

    public ProductAttributes findByProductAttributeId(String productAttributeId);

    public boolean checkProductAttributeExists(String productId, Color color, String size);

    // Admin

    public boolean disableProductAttribute(String productAttributeId);

    public boolean enableProductAttribute(String productAttributeId);

    public Page<ProductAttributes> findAllByProductId(String productId, Pageable pageable);

    public boolean updateProductAttribute(UpdateProductAttributeRequest updateProductAttributeRequest);

    boolean addProductAttribute(AddProductAttributeRequest addProductAttributeRequest);
}
