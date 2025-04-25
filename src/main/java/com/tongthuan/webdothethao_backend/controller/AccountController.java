package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.ChangePasswordRequest;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.LoginRequest;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.ResetPasswordRequest;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.UserStatsResponse;
import com.tongthuan.webdothethao_backend.dto.response.JwtResponse;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.service.AccountService;
import com.tongthuan.webdothethao_backend.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @PostMapping("/Register")
    public ResponseEntity<?> registerNewAccount(@Validated @RequestBody Users user) {
        ResponseEntity<?> response = accountService.registerAccount(user);
        return response;
    }

    @GetMapping("/Active")
    public ResponseEntity<?> activeAccount(@RequestParam("email") String email, @RequestParam("activeCode") String activeCode) {
        ResponseEntity<?> response = accountService.activeAccount(email, activeCode);
        return response;
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassWord()));
            if (authentication.isAuthenticated()) {
                final String jwt = jwtService.generateToken(loginRequest.getUserName());
                return ResponseEntity.ok().body(new JwtResponse(jwt));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Tên đăng nhập hoặc mật khẩu không chính xác");
        }
        return ResponseEntity.badRequest().body("Xác thực không thành công");
    }

    @DeleteMapping("/lockAccount")
    public ResponseEntity<?> lockAccount(@RequestParam("userId") String userId) {
        if (userId.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().body("nguoi dung khong ton tai");
        }

        if(accountService.lockAccount(userId) == null)
        {
            return ResponseEntity.badRequest().body("nguoi dung khong ton tai");
        }

        return ResponseEntity.ok().body("true");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        boolean result = accountService.changePassword(changePasswordRequest);

        if (result) return ResponseEntity.ok().body("doi mat khau thanh cong");

        return ResponseEntity.badRequest().body("thong tin tai khoan hoac mat khau khong chinh xac!");

    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email)
    {
        if(!accountService.forgotPassword(email))
            return ResponseEntity.badRequest().body("email khong ton tai hoac gap loi trong qua trinh xu ly!");
        return ResponseEntity.ok().build();
    }


    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest)
    {
        if(!accountService.resetPassword(resetPasswordRequest))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }




}
