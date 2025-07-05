package com.tongthuan.webdothethao_backend.dto.adminRequest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
