package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BrandService {

//   User
    public Optional<Brand> findByProductID(String productId);


//    Admin
    public Page<Brand> getAllBrand(Pageable pageable);

    public List<Brand> getAllBrands();

}
