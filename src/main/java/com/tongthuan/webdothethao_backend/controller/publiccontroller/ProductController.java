package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.dto.response.ProductsResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products") // Chữ thường
public class ProductController {

    @Autowired
    private ProductsService productsServiceInterface;

    @Autowired
    private PagedResourcesAssembler<ProductsResponse> pagedAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductsResponse> productsPage = productsServiceInterface.getAllProducts(pageable)
                .map(ProductsResponse::new);

        // Convert Page<ProductsResponse> to PagedModel<EntityModel<ProductsResponse>>
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsPage);

        return ResponseEntity.ok(pagedModel);
    }



}
