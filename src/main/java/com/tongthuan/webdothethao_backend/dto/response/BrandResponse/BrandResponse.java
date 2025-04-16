package com.tongthuan.webdothethao_backend.dto.response.BrandResponse;

import com.tongthuan.webdothethao_backend.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private int brandId;
    private String brandName;
    private String country;

    public BrandResponse(Brand brand) {
        this.brandId = brand.getBrandId();
        this.brandName = brand.getBrandName();
        this.country = brand.getCountry();
    }
}
