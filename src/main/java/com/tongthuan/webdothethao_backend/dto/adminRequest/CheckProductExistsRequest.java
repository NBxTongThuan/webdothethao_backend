package com.tongthuan.webdothethao_backend.dto.adminRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckProductExistsRequest {
    private String productName;
    private String typeName;
    private String brandName;

}
