package com.tongthuan.webdothethao_backend.dto.adminRequest;

import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ImageRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductAttributeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    private String productId;
    private String productName;
    private String description;
    private long importPrice;
    private Long price;
    private int brandId;
    private int typeId;
    private List<UpdateImageRequest> listUpdateImage;
}
