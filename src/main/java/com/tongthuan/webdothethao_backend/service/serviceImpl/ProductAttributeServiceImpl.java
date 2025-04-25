package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.AddProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductAttributeRequest;
import com.tongthuan.webdothethao_backend.entity.ProductAttributes;
import com.tongthuan.webdothethao_backend.entity.Products;
import com.tongthuan.webdothethao_backend.repository.ProductAttributesRepository;
import com.tongthuan.webdothethao_backend.repository.ProductsRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributesRepository productAttributesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public List<ProductAttributes> findByProductId(String productId) {
        return productAttributesRepository.findByProductId(productId);
    }

    @Override
    public ProductAttributes findByProductAttributeId(String productAttributeId) {
        return productAttributesRepository.findByProductAttributeId(productAttributeId);
    }

    @Override
    public boolean checkProductAttributeExists(String productId, Color color, String size) {
        ProductAttributes productAttribute = productAttributesRepository.findByColorAndSize(productId, color, size);
        if (productAttribute != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean disableProductAttribute(String productAttributeId) {

        ProductAttributes productAttribute = productAttributesRepository.findByProductAttributeId(productAttributeId);
        if (productAttribute == null)
            return false;
        productAttribute.setEnable(false);
        productAttributesRepository.saveAndFlush(productAttribute);
        return true;
    }

    @Override
    public boolean enableProductAttribute(String productAttributeId) {
        ProductAttributes productAttribute = productAttributesRepository.findByProductAttributeId(productAttributeId);
        if (productAttribute == null)
            return false;
        productAttribute.setEnable(true);
        productAttributesRepository.saveAndFlush(productAttribute);
        return true;
    }

    @Override
    public Page<ProductAttributes> findAllByProductId(String productId, Pageable pageable) {
        return productAttributesRepository.finAllByProductId(productId, pageable);
    }

    @Override
    public boolean updateProductAttribute(UpdateProductAttributeRequest updateProductAttributeRequest) {
        ProductAttributes productAttribute = productAttributesRepository.findByProductAttributeId(updateProductAttributeRequest.getProductAttributeId());
        System.out.println(updateProductAttributeRequest.getProductAttributeId());
        if (productAttribute == null)
            return false;
        productAttribute.setQuantity(updateProductAttributeRequest.getQuantity());
        productAttributesRepository.saveAndFlush(productAttribute);
        return true;
    }

    @Override
    public boolean addProductAttribute(AddProductAttributeRequest addProductAttributeRequest) {

        Products product = productsRepository.findById(addProductAttributeRequest.getProductId()).orElse(null);
        if (product == null)
            return false;

        ProductAttributes productAttribute = new ProductAttributes();
        productAttribute.setProduct(product);
        productAttribute.setSize(addProductAttributeRequest.getSize());
        productAttribute.setColor(addProductAttributeRequest.getColor());
        productAttribute.setQuantity(addProductAttributeRequest.getQuantity());
        productAttribute.setEnable(true);
        productAttribute.setQuantitySold(0);
        productAttributesRepository.saveAndFlush(productAttribute);
        return true;
    }
}
