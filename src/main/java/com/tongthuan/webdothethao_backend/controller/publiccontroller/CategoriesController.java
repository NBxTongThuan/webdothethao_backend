package com.tongthuan.webdothethao_backend.controller.publiccontroller;

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

import com.tongthuan.webdothethao_backend.dto.response.CategoryResponse.CategoryResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CategoriesService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private PagedResourcesAssembler<CategoryResponse> categoryResponsePagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCate() {
        return ResponseEntity.ok()
                .body(categoriesService.findALl().stream()
                        .map(CategoryResponse::new)
                        .toList());
    }

    @GetMapping("/top-4")
    public ResponseEntity<PagedModel<EntityModel<CategoryResponse>>> getTopCategory(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponse> categoryResponses =
                categoriesService.findTopCategoriesByProductCount(pageable).map(CategoryResponse::new);
        PagedModel<EntityModel<CategoryResponse>> pagedModel =
                categoryResponsePagedResourcesAssembler.toModel(categoryResponses);
        return ResponseEntity.ok(pagedModel);
    }
}
