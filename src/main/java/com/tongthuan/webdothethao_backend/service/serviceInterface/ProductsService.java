package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateProductRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductRequest;
import com.tongthuan.webdothethao_backend.entity.Products;

public interface ProductsService {
    List<Products> findAll();

    Page<Products> getAllProducts(Pageable pageable);

    Optional<Products> getByProductId(String productId);

    Page<Products> getListProductsByCategoryId(int categoryId, Pageable pageable);

    Page<Products> getListProductsByProductName(String productName, Pageable pageable);

    boolean addProduct(ProductRequest productRequest);

    boolean disableInStock(String productId);

    boolean inStock(String productId);

    boolean updateProduct(UpdateProductRequest updateProductRequest);

    boolean checkExistsByProductName(String productName, String typeName, String brandName);

    Long getCountProductIsInStock();

    Page<Products> findTop4Selling(Pageable pageable);

    Page<Products> getNewestProduct(Pageable pageable);

    Page<Products> getSameProductType(Pageable pageable, String productId);

    Page<Products> getDiscountingProducts(Pageable pageable);

    boolean updateDiscountPrice(String productId, long moneyOff);

    Page<Products> getInStockProducts(Pageable pageable);

    Products findById(String productId);

    Page<Products> findTopSale(Pageable pageable);

    Page<Products> findTopSlowSale(Pageable pageable);

    Page<Products> findByCategoryAndPrice(Pageable pageable, int categoryId, long price);

    Page<Products> findByPrice(Pageable pageable, long price);
}
