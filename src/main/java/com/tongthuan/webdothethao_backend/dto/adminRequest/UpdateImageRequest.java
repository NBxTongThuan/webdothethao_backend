package com.tongthuan.webdothethao_backend.dto.adminRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateImageRequest {
    private String imageId;
    private String imageName;
    private String url;
    private String data;
}
