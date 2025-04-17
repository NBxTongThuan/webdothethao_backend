package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.BrandResponse.BrandResponse;
import com.tongthuan.webdothethao_backend.dto.response.ProductsResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.BrandService;
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
@RequestMapping("/api/admin/brands")
public class BrandControllerAdmin {

    @Autowired
    private BrandService brandService;

    @Autowired
    private PagedResourcesAssembler<BrandResponse> brandResponsePagedResourcesAssembler;

    @GetMapping("/getAll")
    public ResponseEntity<PagedModel<EntityModel<BrandResponse>>> getAllBrand(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<BrandResponse> brandResponses = brandService.getAllBrand(pageable).map(BrandResponse::new);

        if(brandResponses.isEmpty())
        {
            return ResponseEntity.badRequest().build();
        }

        PagedModel<EntityModel<BrandResponse>> pagedModel = brandResponsePagedResourcesAssembler.toModel(brandResponses);

        return ResponseEntity.ok(pagedModel);
    }

}
