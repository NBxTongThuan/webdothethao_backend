package com.tongthuan.webdothethao_backend.controller.AdminController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.response.UserDetailResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UserDetailService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/user-detail")
public class AdminUserDetailController {

    @Autowired
    private UserDetailService userDetailService;

    @GetMapping("/get-by-name")
    public ResponseEntity<Optional<UserDetailResponse>> getUserDetailByUserName(
            @RequestParam("userName") String userName) {
        if (userName.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok()
                .body(userDetailService.findByUserName(userName).map(UserDetailResponse::new));
    }
}
