package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.UserStatsResponse;
import com.tongthuan.webdothethao_backend.dto.response.UserResponse.UserResponse;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users") // Chữ thường
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PagedResourcesAssembler<UserResponse> pagedAssembler;


    @GetMapping("/check-exists-by-user-name")
    public boolean checkExistsByUserName(@RequestParam("userName") String userName)
    {
        return usersService.checkExistsByUserName(userName);
    }

    @GetMapping("/check-exists-by-email")
    public boolean checkExistsByEmail(@RequestParam("email") String email)
    {
        return usersService.checkExistsByEmail(email);
    }


}
