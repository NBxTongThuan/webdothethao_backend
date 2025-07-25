package com.tongthuan.webdothethao_backend.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateImageRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateProductRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ImageRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductRequest;
import com.tongthuan.webdothethao_backend.entity.*;
import com.tongthuan.webdothethao_backend.repository.*;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductsService;

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

    @Autowired
    private ImagesRepository imagesRepository;

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
        return productsRepository.findProductsByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<Products> getListProductsByProductName(String productName, Pageable pageable) {
        return productsRepository.findProductsByProductName(productName, pageable);
    }

    //    Admin
    @Override
    public boolean addProduct(ProductRequest productRequest) {

        Brand brand = brandRepository.findById(productRequest.getBrandId()).orElse(null);
        if (brand == null) return false;

        Types type = typesRepository.findById(productRequest.getTypeId()).orElse(null);
        if (type == null) {
            return false;
        }
        Products product = new Products();
        product.setBrand(brand);
        product.setType(type);
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setPrice(productRequest.getPrice());
        product.setImportPrice(productRequest.getImportPrice());
        product.setCreatedDate(LocalDateTime.now());
        product.setInStock(true);

        List<ProductAttributes> productAttributesList = new ArrayList<>();

        for (ProductAttributeRequest productAttributeRequest : productRequest.getListProductAttribute()) {
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

        for (ImageRequest imageRequest : productRequest.getListImage()) {
            Images image = new Images();
            image.setData(imageRequest.getData());
            image.setUrl(imageRequest.getUrl());
            image.setName(imageRequest.getName());
            image.setProduct(product);
            imagesList.add(image);
        }

        product.setListProductAttributes(productAttributesList);
        product.setListImage(imagesList);
        productsRepository.saveAndFlush(product);
        return true;
    }

    @Override
    public boolean disableInStock(String productId) {

        Products product = productsRepository.findByProductId(productId);
        if (product == null) return false;
        product.setInStock(false);
        productsRepository.saveAndFlush(product);
        return true;
    }

    @Override
    public boolean inStock(String productId) {
        Products product = productsRepository.findByProductId(productId);
        if (product == null) return false;
        product.setInStock(true);
        productsRepository.saveAndFlush(product);
        return true;
    }

    @Override
    public boolean updateProduct(UpdateProductRequest updateProductRequest) {

        Products product = productsRepository.findByProductId(updateProductRequest.getProductId());
        if (product == null) return false;

        Types type = typesRepository.findById(updateProductRequest.getTypeId()).orElse(null);
        if (type == null) return false;
        product.setType(type);

        Brand brand =
                brandRepository.findById(updateProductRequest.getBrandId()).orElse(null);
        if (brand == null) return false;
        product.setBrand(brand);

        product.setDescription(updateProductRequest.getDescription());
        product.setPrice(updateProductRequest.getPrice());
        product.setProductName(updateProductRequest.getProductName());
        System.out.println(updateProductRequest.getImportPrice());
        product.setImportPrice(updateProductRequest.getImportPrice());

        // handle Image
        if (!updateProductRequest.getListUpdateImage().isEmpty()) {
            List<Images> imagesList = new ArrayList<>();
            for (UpdateImageRequest updateImageRequest : updateProductRequest.getListUpdateImage()) {
                if (!updateImageRequest.getImageId().equalsIgnoreCase("")) {
                    Images image = imagesRepository
                            .findById(updateImageRequest.getImageId())
                            .orElse(null);
                    if (image != null) {
                        image.setProduct(product);
                        image.setData(updateImageRequest.getData());
                        image.setUrl(updateImageRequest.getUrl());
                        image.setName(updateImageRequest.getName());
                        imagesList.add(image);
                    }
                } else {
                    Images image = new Images();
                    image.setProduct(product);
                    image.setData(updateImageRequest.getData());
                    image.setUrl(updateImageRequest.getUrl());
                    image.setName(updateImageRequest.getName());
                    imagesList.add(image);
                }
            }
            if (!imagesList.isEmpty()) {
                imagesRepository.saveAll(imagesList);
            }
        }

        productsRepository.saveAndFlush(product);
        return true;
    }

    @Override
    public boolean checkExistsByProductName(String productName, String typeName, String brandName) {
        return productsRepository
                .findProductsByProductNameAndTypeName(productName, typeName, brandName)
                .isPresent();
    }

    @Override
    public Long getCountProductIsInStock() {
        return productsRepository.countProductInStock();
    }

    @Override
    public Page<Products> findTop4Selling(Pageable pageable) {
        return productsRepository.findTop4BestSellingProducts(pageable);
    }

    @Override
    public Page<Products> getNewestProduct(Pageable pageable) {
        return productsRepository.findTopNewestProduct(pageable);
    }

    @Override
    public Page<Products> getSameProductType(Pageable pageable, String productId) {
        Products product = productsRepository.findByProductId(productId);
        return productsRepository.findSameTypeProducts(pageable, productId, product.getType());
    }

    @Override
    public Page<Products> getDiscountingProducts(Pageable pageable) {
        return productsRepository.findDiscountingProduct(pageable);
    }

    @Override
    public boolean updateDiscountPrice(String productId, long moneyOff) {
        Products product = productsRepository.findByProductId(productId);
        if (product == null) return false;
        product.setMoneyOff(moneyOff);
        productsRepository.save(product);
        return true;
    }

    @Override
    public Page<Products> getInStockProducts(Pageable pageable) {
        return productsRepository.findInStockProducts(pageable);
    }

    @Override
    public Products findById(String productId) {
        return productsRepository.findById(productId).orElse(null);
    }

    @Override
    public Page<Products> findTopSale(Pageable pageable) {
        return productsRepository.findTopSale(pageable);
    }

    @Override
    public Page<Products> findTopSlowSale(Pageable pageable) {
        return productsRepository.findTopSlowSale(pageable);
    }

    @Override
    public Page<Products> findByCategoryAndPrice(Pageable pageable, int categoryId, long price) {
        return productsRepository.findByCategoryAndPrice(pageable, categoryId, price);
    }

    @Override
    public Page<Products> findByPrice(Pageable pageable, long price) {
        return productsRepository.findByPrice(pageable, price);
    }
}
