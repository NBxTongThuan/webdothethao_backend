package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.entity.ProductAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductAttributeResponse {
    private String productAttributeId;
    private Color color;
    private String size;
    private int quantity;
    private int quantitySold;
    private boolean enable;

    public AdminProductAttributeResponse(ProductAttributes productAttributes) {
        this.productAttributeId = productAttributes.getProductAttributeId();
        this.color = productAttributes.getColor();
        this.size = productAttributes.getSize();
        this.quantity = productAttributes.getQuantity();
        this.quantitySold = productAttributes.getQuantitySold();
        this.enable = productAttributes.isEnable();
    }
}
