package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.TypeResponse;
import com.tongthuan.webdothethao_backend.entity.Types;
import com.tongthuan.webdothethao_backend.service.serviceInterface.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/types")
public class TypeControllerAdmin {

    @Autowired
    private TypeService typeService;

    @GetMapping("/getAllType")
    public ResponseEntity<List<TypeResponse>> getAllType(){
        List<Types> typesList = typeService.getAllTypes();
        if(typesList.isEmpty())
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(typesList.stream().map(TypeResponse::new).toList());
    }

}
