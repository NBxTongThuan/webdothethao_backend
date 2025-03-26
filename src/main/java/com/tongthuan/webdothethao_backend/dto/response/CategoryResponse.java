package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private int categoriesId;
    private String categoriesName;

    public CategoryResponse(Categories categories) {
        this.categoriesId = categories.getCategoriesId();
        this.categoriesName = categories.getCategoriesName();
    }
}
