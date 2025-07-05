package com.tongthuan.webdothethao_backend.controller.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AddCategoryRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateCategoryRequest;
import com.tongthuan.webdothethao_backend.dto.response.CategoryResponse.CategoryResponse;
import com.tongthuan.webdothethao_backend.entity.Categories;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CategoriesService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private PagedResourcesAssembler<CategoryResponse> pagedResourcesAssembler;

    @GetMapping("/find-all")
    public ResponseEntity<List<CategoryResponse>> findAllCategory() {
        return ResponseEntity.ok(
                categoriesService.findALl().stream().map(CategoryResponse::new).toList());
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<CategoryResponse> findById(@RequestParam("categoryId") int categoryId) {
        Categories category = categoriesService.findById(categoryId).orElse(null);
        if (category == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(new CategoryResponse(category));
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<CategoryResponse> findByName(@RequestParam("categoryName") String categoryName) {
        Categories category = categoriesService.findByName(categoryName);
        if (category == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(new CategoryResponse(category));
    }

    @GetMapping("/get-page")
    public ResponseEntity<PagedModel<EntityModel<CategoryResponse>>> getAllCategory(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponse> categoryResponses =
                categoriesService.findAllPage(pageable).map(CategoryResponse::new);
        if (categoryResponses.isEmpty()) return ResponseEntity.badRequest().build();
        PagedModel<EntityModel<CategoryResponse>> pagedModel = pagedResourcesAssembler.toModel(categoryResponses);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/check-exists")
    public ResponseEntity<Boolean> checkExists(@RequestParam("categoryName") String categoryName) {

        if (categoryName.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }
        boolean result = categoriesService.checkExistsByCategoryName(categoryName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody AddCategoryRequest addCategoryRequest) {
        if (addCategoryRequest == null) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(categoriesService.addCategory(addCategoryRequest));
    }

    @DeleteMapping("/disable")
    public ResponseEntity<?> disableCategoryById(@RequestParam("categoryId") int categoryId) {
        boolean result = categoriesService.deleteCategory(categoryId);
        if (!result) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/enable")
    public ResponseEntity<?> enableCategoryById(@RequestParam("categoryId") int categoryId) {
        boolean result = categoriesService.enableCategory(categoryId);
        if (!result) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest) {
        boolean result = categoriesService.updateCategory(updateCategoryRequest);
        if (!result) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }
}
