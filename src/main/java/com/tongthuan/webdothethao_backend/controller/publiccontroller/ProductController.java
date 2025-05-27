package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.dto.adminRequest.CheckProductExistsRequest;
import com.tongthuan.webdothethao_backend.dto.response.ProductsResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products") // Chữ thường
public class ProductController {

    @Autowired
    private ProductsService productsServiceInterface;

    @Autowired
    private PagedResourcesAssembler<ProductsResponse> pagedAssembler;

    @GetMapping("/get-all")
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

    @GetMapping("/page-by-category-id")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> getListProductsByCategoryId(@RequestParam("categoryId") int categoryId,
                                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductsResponse> productsPage = productsServiceInterface.getListProductsByCategoryId(categoryId, pageable)
                .map(ProductsResponse::new);

        // Convert Page<ProductsResponse> to PagedModel<EntityModel<ProductsResponse>>
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsPage);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/page-by-name")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> getListProductsByProductName(@RequestParam("productName") String productName,
                                                                                                  @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (productName.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductsResponse> productsResponses = productsServiceInterface.getListProductsByProductName(productName, pageable)
                .map(ProductsResponse::new);
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsResponses);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<Optional<ProductsResponse>> getProductById(@RequestParam("productId") String productId) {
        if (productId.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(productsServiceInterface.getByProductId(productId).map(ProductsResponse::new));
    }


    @GetMapping("/top-4")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> findTop4Selling(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductsResponse> productsResponses = productsServiceInterface.findTop4Selling(pageable)
                .map(ProductsResponse::new);
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsResponses);

        return ResponseEntity.ok(pagedModel);

    }


    @GetMapping("/page-newest")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> findTopNewestProduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductsResponse> productsResponses = productsServiceInterface.getNewestProduct(pageable)
                .map(ProductsResponse::new);
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsResponses);

        return ResponseEntity.ok(pagedModel);

    }
    @GetMapping("/same")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> findSameProductType(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size,@RequestParam("productId")String productId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductsResponse> productsResponses = productsServiceInterface.getSameProductType(pageable,productId)
                .map(ProductsResponse::new);
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsResponses);
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/discounting")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> findDiscountingProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductsResponse> productsResponses = productsServiceInterface.getDiscountingProducts(pageable)
                .map(ProductsResponse::new);
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsResponses);
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/get-by-cate-price")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> findByCategoryAndPrice(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size, @RequestParam("categoryId") int categoryId, @RequestParam("price") long price) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price"));

        Page<ProductsResponse> productsResponses = productsServiceInterface.findByCategoryAndPrice(pageable,categoryId,price)
                .map(ProductsResponse::new);
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsResponses);
        return ResponseEntity.ok(pagedModel);

    }

    @GetMapping("/get-by-price")
    public ResponseEntity<PagedModel<EntityModel<ProductsResponse>>> findByPrice(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size, @RequestParam("price") long price) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price"));

        Page<ProductsResponse> productsResponses = productsServiceInterface.findByPrice(pageable,price)
                .map(ProductsResponse::new);
        PagedModel<EntityModel<ProductsResponse>> pagedModel = pagedAssembler.toModel(productsResponses);
        return ResponseEntity.ok(pagedModel);

    }




}
