package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.response.ProductsResponse;
import com.tongthuan.webdothethao_backend.dto.response.UserResponse;
import com.tongthuan.webdothethao_backend.entity.Products;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import lombok.RequiredArgsConstructor;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users") // Chữ thường
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PagedResourcesAssembler<UserResponse> pagedAssembler;


    @GetMapping("/checkExistsByUserName")
    public boolean checkExistsByUserName(@RequestParam("userName") String userName)
    {
        return usersService.checkExistsByUserName(userName);
    }

    @GetMapping("/checkExistsByEmail")
    public boolean checkExistsByEmail(@RequestParam("email") String email)
    {
        return usersService.checkExistsByEmail(email);
    }

    @GetMapping("/allUser")
    public ResponseEntity<PagedModel<EntityModel<UserResponse>>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) { // Trả về List<Users>

        Pageable pageable = PageRequest.of(page,size);
        Page<UserResponse> usersResponse = usersService.findAllUsersPage(pageable).map(UserResponse::new);

        PagedModel<EntityModel<UserResponse>> pagedModel = pagedAssembler.toModel(usersResponse);

        return ResponseEntity.ok(pagedModel);
    }

}
