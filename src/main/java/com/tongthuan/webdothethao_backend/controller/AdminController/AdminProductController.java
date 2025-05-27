package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.adminRequest.CheckProductExistsRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateProductRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.ProductRequest;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.AdminProductsResponse;
import com.tongthuan.webdothethao_backend.dto.response.ProductsResponse;
import com.tongthuan.webdothethao_backend.entity.Products;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private PagedResourcesAssembler<AdminProductsResponse> productsResponsePagedResourcesAssembler;

    @GetMapping("/get-page")
    public ResponseEntity<PagedModel<EntityModel<AdminProductsResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminProductsResponse> productsPage = productsService.getAllProducts(pageable)
                .map(AdminProductsResponse::new);

        PagedModel<EntityModel<AdminProductsResponse>> pagedModel = productsResponsePagedResourcesAssembler.toModel(productsPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/get-page-discount")
    public ResponseEntity<PagedModel<EntityModel<AdminProductsResponse>>> getDiscountingProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminProductsResponse> productsPage = productsService.getDiscountingProducts(pageable)
                .map(AdminProductsResponse::new);

        PagedModel<EntityModel<AdminProductsResponse>> pagedModel = productsResponsePagedResourcesAssembler.toModel(productsPage);
        return ResponseEntity.ok(pagedModel);
    }

    @PutMapping("/update-discount")
    public ResponseEntity<Boolean> updateDiscountingPrice(@RequestParam("productId") String productId, @RequestParam("moneyOff") long moneyOff) {
        boolean result = productsService.updateDiscountPrice(productId, moneyOff);
        if (result) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @PutMapping("/stop-discount")
    public ResponseEntity<Boolean> stoppingDiscount(@RequestParam("productId") String productId) {
        boolean result = productsService.updateDiscountPrice(productId, 0);
        if (result) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }


    @PostMapping("/add")
    public ResponseEntity<Boolean> addProduct(@RequestBody ProductRequest productRequest) {
        boolean result = productsService.addProduct(productRequest);
        if (!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok().body(true);
    }

    @DeleteMapping("/disable")
    public ResponseEntity<Boolean> disInStockProduct(@RequestParam("productId") String productId) {
        boolean result = productsService.disableInStock(productId);
        if (!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok().body(true);
    }

    @PutMapping("/enable")
    public ResponseEntity<Boolean> inStockProduct(@RequestParam("productId") String productId) {
        boolean result = productsService.inStock(productId);
        if (!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok().body(true);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateProduct(@RequestBody UpdateProductRequest updateProductRequest) {
        boolean result = productsService.updateProduct(updateProductRequest);
        if (!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/check-exists")
    public ResponseEntity<Boolean> checkExistsByProductNameTypeNameBrandName(@RequestBody CheckProductExistsRequest checkProductExistsRequest) {
        boolean result = productsService.checkExistsByProductName(checkProductExistsRequest.getProductName(), checkProductExistsRequest.getTypeName(), checkProductExistsRequest.getBrandName());
        if (!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);

    }

    @GetMapping("/get-count-enable")
    public ResponseEntity<Long> getCountIsInStockProduct() {
        return ResponseEntity.ok(productsService.getCountProductIsInStock());
    }

    @GetMapping("/get-enable")
    public ResponseEntity<PagedModel<EntityModel<AdminProductsResponse>>> getInStockProducts(@RequestParam(defaultValue = "0") int page,
                                                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminProductsResponse> productsPage = productsService.getInStockProducts(pageable)
                .map(AdminProductsResponse::new);

        PagedModel<EntityModel<AdminProductsResponse>> pagedModel = productsResponsePagedResourcesAssembler.toModel(productsPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<AdminProductsResponse> findById(String productId) {

        Products products = productsService.findById(productId);

        return ResponseEntity.ok(new AdminProductsResponse(products));

    }

    @GetMapping("/get-top-sale")
    public ResponseEntity<PagedModel<EntityModel<AdminProductsResponse>>> findTopSale(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "quantitySold"));

        Page<AdminProductsResponse> productsPage = productsService.findTopSale(pageable)
                .map(AdminProductsResponse::new);

        PagedModel<EntityModel<AdminProductsResponse>> pagedModel = productsResponsePagedResourcesAssembler.toModel(productsPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/get-top-slow-sale")
    public ResponseEntity<PagedModel<EntityModel<AdminProductsResponse>>> findTopSlowSale(@RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "quantitySold"));

        Page<AdminProductsResponse> productsPage = productsService.findTopSlowSale(pageable)
                .map(AdminProductsResponse::new);

        PagedModel<EntityModel<AdminProductsResponse>> pagedModel = productsResponsePagedResourcesAssembler.toModel(productsPage);
        return ResponseEntity.ok(pagedModel);
    }


}
