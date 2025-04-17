package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.Images;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private String imageId;
    private String data;
    private String url;
    private String name;

    public ImageResponse(Images images) {
        this.imageId = images.getImageId();
        this.data = images.getData();
        this.url = images.getUrl();
        this.name = images.getName();
    }
}
