package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.constantvalue.ResponseCode;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.ChangePasswordRequest;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.LoginRequest;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.ResetPasswordRequest;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.UserStatsResponse;
import com.tongthuan.webdothethao_backend.dto.response.JwtResponse;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.service.AccountService;
import com.tongthuan.webdothethao_backend.service.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

//    @PostMapping("/Login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassWord()));
//            if (authentication.isAuthenticated()) {
//                final String jwtToken = jwtService.generateToken(loginRequest.getUserName());
//
//
//                Cookie cookie = new Cookie("token", jwtToken);
//                cookie.setHttpOnly(true);
//                cookie.setSecure(true); // nếu dev local thì có thể tạm để false
//                cookie.setPath("/");
//                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
//
//                response.addCookie(cookie);
//
//                return ResponseEntity.ok().body(
//                        Map.of("statusCode", ResponseCode.SUCCESS,
//                                "message", "Đăng nhập thành công")
//                );
//            }
//        } catch (AuthenticationException e) {
//            return ResponseEntity.badRequest().body("Tên đăng nhập hoặc mật khẩu không chính xác");
//        }
//        return ResponseEntity.badRequest().body("Xác thực không thành công");
//    }

    @DeleteMapping("/lockAccount")
    public ResponseEntity<?> lockAccount(@RequestParam("userId") String userId) {
        if (userId.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().body("nguoi dung khong ton tai");
        }

        if (accountService.lockAccount(userId) == null) {
            return ResponseEntity.badRequest().body("nguoi dung khong ton tai");
        }

        return ResponseEntity.ok().body("true");
    }


    @PutMapping("/unLockAccount")
    public ResponseEntity<?> unLockAccount(@RequestParam("userId") String userId) {
        if (userId.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().body("nguoi dung khong ton tai");
        }

        if (accountService.unLockAccount(userId) == null) {
            return ResponseEntity.badRequest().body("nguoi dung khong ton tai");
        }

        return ResponseEntity.ok().body("true");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean result = accountService.changePassword(changePasswordRequest);

        if (result) return ResponseEntity.ok().body("doi mat khau thanh cong");

        return ResponseEntity.badRequest().body("thong tin tai khoan hoac mat khau khong chinh xac!");

    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        if (!accountService.forgotPassword(email))
            return ResponseEntity.badRequest().body("email khong ton tai hoac gap loi trong qua trinh xu ly!");
        return ResponseEntity.ok().build();
    }


    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        if (!accountService.resetPassword(resetPasswordRequest))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }


}
