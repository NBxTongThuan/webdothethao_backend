package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.adminRequest.AddTypesRequest;
import com.tongthuan.webdothethao_backend.dto.adminRequest.UpdateTypeRequest;
import com.tongthuan.webdothethao_backend.dto.response.TypeResponse;
import com.tongthuan.webdothethao_backend.entity.Types;
import com.tongthuan.webdothethao_backend.service.serviceInterface.TypeService;
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
@RequestMapping("/api/admin/types")
public class TypeControllerAdmin {

    @Autowired
    private TypeService typeService;

    @Autowired
    private PagedResourcesAssembler<TypeResponse> pagedResourcesAssembler;

    @GetMapping("/getAllType")
    public ResponseEntity<PagedModel<EntityModel<TypeResponse>>> getAllType(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {


        Pageable pageable = PageRequest.of(page, size);
        Page<TypeResponse> typesList = typeService.getAllTypes(pageable).map(TypeResponse::new);
        if (typesList.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        PagedModel<EntityModel<TypeResponse>> pagedModel = pagedResourcesAssembler.toModel(typesList);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/checkExistsType")
    public ResponseEntity<?> checkExistsByIdAndCategoryId(@RequestParam("typeName") String typeName, @RequestParam("categoryName") String categoryName) {
        boolean result = typeService.checkExists(typeName, categoryName);
        if (!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/addTypes")
    public ResponseEntity<?> addType(@RequestBody AddTypesRequest addTypesRequest) {
        if (addTypesRequest == null)
            return ResponseEntity.badRequest().build();
        boolean result = typeService.addType(addTypesRequest);
        if (!result)
            return ResponseEntity.ok(false);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/disableType")
    public ResponseEntity<?> disableType(@RequestParam("typeId") int typeId) {
        boolean result = typeService.disableType(typeId);
        if(!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/enableType")
    public ResponseEntity<?> enableType(@RequestParam("typeId") int typeId) {
        boolean result = typeService.enableType(typeId);
        if(!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/updateType")
    public ResponseEntity<?> enableType(@RequestBody UpdateTypeRequest updateTypeRequest) {
        boolean result = typeService.updateType(updateTypeRequest);
        if(!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }


}
