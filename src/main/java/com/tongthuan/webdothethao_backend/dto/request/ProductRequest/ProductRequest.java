package com.tongthuan.webdothethao_backend.dto.request.ProductRequest;

import com.tongthuan.webdothethao_backend.entity.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String productName;
    private String productDescription;
    private Long price;
    private int brandId;
    private int typeId;
    private List<ProductAttributeRequest> listProductAttribute;
    private List<ImageRequest> listImage;
}
