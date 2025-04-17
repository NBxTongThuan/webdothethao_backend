package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ImageRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductRequest;
import com.tongthuan.webdothethao_backend.entity.*;
import com.tongthuan.webdothethao_backend.repository.BrandRepository;
import com.tongthuan.webdothethao_backend.repository.ProductAttributesRepository;
import com.tongthuan.webdothethao_backend.repository.ProductsRepository;
import com.tongthuan.webdothethao_backend.repository.TypesRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private TypesRepository typesRepository;

    @Autowired
    private ProductAttributeService productAttributeService;

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

    @Override
    public Page<Products> getListProductsByCategoryId(int categoryId, Pageable pageable) {
        return productsRepository.findProductsByCategoryId(categoryId,pageable);
    }

    @Override
    public Page<Products> getListProductsByProductName(String productName, Pageable pageable) {
        return productsRepository.findProductsByProductName(productName,pageable);
    }




//    Admin
    @Override
    public boolean addProduct(ProductRequest productRequest) {

        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElse(null);
        if(brand == null)
            return false;

        Types type = typesRepository.findById(productRequest.getTypeId()).orElse(null);
        if(type == null)
        {
            return false;
        }
        Products product = new Products();
        product.setBrand(brand);
        product.setType(type);
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getPrice());

        List<ProductAttributes> productAttributesList = new ArrayList<>();

        for(ProductAttributeRequest productAttributeRequest: productRequest.getListProductAttribute())
        {
//            boolean chec = productAttributeService.checkProductAttributeExists(product)
            ProductAttributes productAttribute = new ProductAttributes();
            productAttribute.setColor(productAttributeRequest.getColor());
            productAttribute.setSize(productAttributeRequest.getSize());
            productAttribute.setQuantity(productAttributeRequest.getQuantity());
            productAttribute.setEnable(true);
            productAttribute.setProduct(product);
            productAttributesList.add(productAttribute);
        }

        List<Images> imagesList = new ArrayList<>();

        for(ImageRequest imageRequest : productRequest.getListImage())
        {
            Images image = new Images();
            image.setData(imageRequest.getData());
            image.setUrl(imageRequest.getUrl());
            image.setName(imageRequest.getImageName());
            image.setProduct(product);
            imagesList.add(image);
        }

        product.setListProductAttributes(productAttributesList);
        product.setListImage(imagesList);
        productsRepository.saveAndFlush(product);
        return true;
    }


}
