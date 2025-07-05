package com.tongthuan.webdothethao_backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.UserDetailRequest;
import com.tongthuan.webdothethao_backend.dto.response.UserDetailResponse;
import com.tongthuan.webdothethao_backend.entity.UserDetail;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UserDetailService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user-detail")
public class UserDetailController {

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

    @PutMapping("/update")
    public ResponseEntity<?> updateUserDetail(@RequestBody UserDetailRequest userDetailRequest) {
        UserDetail userDetails = userDetailService.updateUserDetail(userDetailRequest);

        if (userDetails == null) {
            return ResponseEntity.badRequest().body("loi khong xac dinh!");
        }
        return ResponseEntity.ok().body("cap nhat thong tin nguoi dung thanh cong");
    }
}
