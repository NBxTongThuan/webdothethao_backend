package com.tongthuan.webdothethao_backend.dto.request.ProductRequest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String productName;
    private String productDescription;
    private long price;
    private long importPrice;
    private int brandId;
    private int typeId;
    private List<ProductAttributeRequest> listProductAttribute;
    private List<ImageRequest> listImage;
}
