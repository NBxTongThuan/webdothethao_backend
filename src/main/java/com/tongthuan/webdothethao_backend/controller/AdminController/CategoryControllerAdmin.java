package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.CategoryResponse.CategoryResponse;
import com.tongthuan.webdothethao_backend.entity.Categories;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/categories")
public class CategoryControllerAdmin {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/getAllCategory")
    public ResponseEntity<List<CategoryResponse>> getAllCategory()
    {
        List<Categories> categoriesList = categoriesService.findAll();
        if(categoriesList.isEmpty())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(categoriesList.stream().map(CategoryResponse::new).toList());
    }

}
