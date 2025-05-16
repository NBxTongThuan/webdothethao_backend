package com.tongthuan.webdothethao_backend.dto.request.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {

    private String name;
    private String url;
    private String data;
}
