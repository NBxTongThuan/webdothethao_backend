package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.CategoryResponse.CategoryResponse;
import com.tongthuan.webdothethao_backend.entity.Categories;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/categories")
public class CategoryControllerAdmin {

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private PagedResourcesAssembler<CategoryResponse> pagedResourcesAssembler;

    @GetMapping("/getAllCategory")
    public ResponseEntity<PagedModel<EntityModel<CategoryResponse>>> getAllCategory(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponse> categoryResponses = categoriesService.findAllPage(pageable).map(CategoryResponse::new);
        if (categoryResponses.isEmpty())
            return ResponseEntity.badRequest().build();
        PagedModel<EntityModel<CategoryResponse>> pagedModel = pagedResourcesAssembler.toModel(categoryResponses);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/checkCategoryExists")
    public ResponseEntity<Boolean> checkExists(@RequestParam("categoryName") String categoryName) {

        if (categoryName.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = categoriesService.checkExistsByCategoryName(categoryName);
        return ResponseEntity.ok(result);
    }


}
