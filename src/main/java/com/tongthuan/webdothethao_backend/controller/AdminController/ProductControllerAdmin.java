package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductRequest;
import com.tongthuan.webdothethao_backend.dto.response.ProductsResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/products")
public class ProductControllerAdmin {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private PagedResourcesAssembler<ProductsResponse> productsResponsePagedResourcesAssembler;

    @GetMapping("/getAllProduct")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductsResponse> productsPage = productsService.getAllProducts(pageable)
                .map(ProductsResponse::new);
        // Convert Page<ProductsResponse> to PagedModel<EntityModel<ProductsResponse>>
        PagedModel<EntityModel<ProductsResponse>> pagedModel = productsResponsePagedResourcesAssembler.toModel(productsPage);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Boolean> addProduct(@RequestBody ProductRequest productRequest) {
        boolean result = productsService.addProduct(productRequest);
        if (!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok().body(true);
    }


}
