package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.ProductAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductAttributeService {

    public List<ProductAttributes> findByProductId(String productId);

    public ProductAttributes findByProductAttributeId(String productAttributeId);

    public boolean checkProductAttributeExists(String productId,Color color,String size);

    //Admin

    public Page<ProductAttributes> findAllByProductId(String productId, Pageable pageable);

}
