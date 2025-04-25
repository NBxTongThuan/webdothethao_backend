package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.request.ProductRequest.AddProductAttributeRequest;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.AdminProductAttributeResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
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
@RequestMapping("/api/admin/productAttribute")
public class AdminProductAttributeController {

    @Autowired
    private ProductAttributeService productAttributeService;

    @Autowired
    private PagedResourcesAssembler<AdminProductAttributeResponse> adminProductAttributeResponsePagedResourcesAssembler;

    @GetMapping("/getAllProductAttributeByProductId")
    public ResponseEntity<PagedModel<EntityModel<AdminProductAttributeResponse>>> getAllProductAttributeByProductId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size , @RequestParam("productId") String productId) {
        if (productId.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = PageRequest.of(page,size);

        Page<AdminProductAttributeResponse> adminProductAttributeResponses = productAttributeService.findAllByProductId(productId,pageable).map(AdminProductAttributeResponse::new);

        PagedModel<EntityModel<AdminProductAttributeResponse>> pagedModel = adminProductAttributeResponsePagedResourcesAssembler.toModel(adminProductAttributeResponses);


        return ResponseEntity.ok().body(pagedModel);
    }

    @PostMapping("/addProductAttribute")
    public ResponseEntity<Boolean> addProductAttribute(@RequestBody AddProductAttributeRequest  addProductAttributeRequest)
    {
        boolean result = productAttributeService.addProductAttribute(addProductAttributeRequest);
        if(!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/updateProductAttribute")
    public ResponseEntity<Boolean> updateProductAttribute(@RequestBody UpdateProductAttributeRequest updateProductAttributeRequest)
    {
        boolean result = productAttributeService.updateProductAttribute(updateProductAttributeRequest);
        if(!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/enableProductAttribute")
    public ResponseEntity<Boolean> enableProductAttribute(@RequestParam("productAttributeId") String productAttributeId)
    {
        boolean result = productAttributeService.enableProductAttribute(productAttributeId);
        if(!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/disableProductAttribute")
    public ResponseEntity<Boolean> disableProductAttribute(@RequestParam("productAttributeId") String productAttributeId)
    {
        boolean result = productAttributeService.disableProductAttribute(productAttributeId);
        if(!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }


}
