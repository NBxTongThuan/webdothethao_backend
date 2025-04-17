package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.LoginRequest;
import com.tongthuan.webdothethao_backend.dto.response.JwtResponse;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.service.JWTService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/account")
public class AdminLoginController {


    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;


    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {

            Users user = usersService.findByUserName(loginRequest.getUserName());
            if(user == null)
            {
                return ResponseEntity.badRequest().body("Tên đăng nhập hoặc mật khẩu không chính xác");
            }
            if(!user.getRole().name().equalsIgnoreCase("ADMIN") && !user.getRole().name().equalsIgnoreCase("STAFF"))
            {
                return ResponseEntity.status(403).body("Tài khoản không đủ thẩm quyền");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( loginRequest.getUserName(), loginRequest.getPassWord()));
            if (authentication.isAuthenticated()) {

                final String jwt = jwtService.generateToken(loginRequest.getUserName());
                return ResponseEntity.ok().body(new JwtResponse(jwt));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Tên đăng nhập hoặc mật khẩu không chính xác");
        }
        return ResponseEntity.badRequest().body("Xác thực không thành công");
    }


}
