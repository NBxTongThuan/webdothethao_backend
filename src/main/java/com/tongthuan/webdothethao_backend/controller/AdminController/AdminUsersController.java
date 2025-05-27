package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.TopBuyerResponse;
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

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users") // Chữ thường
public class AdminUsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PagedResourcesAssembler<UserResponse> pagedAssembler;

    @Autowired
    private PagedResourcesAssembler<TopBuyerResponse> pagedAssembler1;

    //Admin
    @GetMapping("/all")
    public ResponseEntity<PagedModel<EntityModel<UserResponse>>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) { // Trả về List<Users>

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> usersResponse = usersService.findAllUsersPage(pageable).map(UserResponse::new);

        PagedModel<EntityModel<UserResponse>> pagedModel = pagedAssembler.toModel(usersResponse);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/stats")
    public ResponseEntity<UserStatsResponse> getUserStats() {
        UserStatsResponse response = usersService.getUserStats();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-buyer")
    public ResponseEntity<PagedModel<EntityModel<TopBuyerResponse>>> getTopBuyer(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TopBuyerResponse> topUsersResponse = usersService.findTopBuyer(pageable);

        PagedModel<EntityModel<TopBuyerResponse>> pagedModel = pagedAssembler1.toModel(topUsersResponse);

        return ResponseEntity.ok(pagedModel);

    }


}
