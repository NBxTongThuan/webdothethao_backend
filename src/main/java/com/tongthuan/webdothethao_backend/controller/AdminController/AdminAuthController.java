package com.tongthuan.webdothethao_backend.controller.AdminController;

import com.tongthuan.webdothethao_backend.constantvalue.ResponseCode;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.LoginRequest;
import com.tongthuan.webdothethao_backend.dto.response.JwtResponse;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.service.JWTService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/auth")
public class AdminAuthController {


    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {

            Users user = usersService.findByUserName(loginRequest.getUserName());
            if(user == null)
            {
                return ResponseEntity.badRequest().body( Map.of("statusCode", ResponseCode.FALSE_USERNAME_OR_PASSWORD,
                        "message", "Tài khoản hoặc mật khẩu không chính xác!"));
            }
            if(!user.getRole().name().equalsIgnoreCase("ADMIN") && !user.getRole().name().equalsIgnoreCase("STAFF"))
            {
                return ResponseEntity.ok().body(
                        Map.of("statusCode", ResponseCode.NO_ACCESS,
                                "message", "Bạn không đủ quyền đăng nhập trang này!")
                );
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( loginRequest.getUserName(), loginRequest.getPassWord()));
            if (authentication.isAuthenticated()) {


                final String jwtToken = jwtService.generateToken(loginRequest.getUserName());
                Cookie cookie = new Cookie("token", jwtToken);
                cookie.setHttpOnly(true);
                cookie.setSecure(false); // nếu dev local thì có thể tạm để false
                cookie.setPath("/");
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                response.addCookie(cookie);

                return ResponseEntity.ok().body(
                        Map.of("statusCode", ResponseCode.SUCCESS,
                                "message", "Đăng nhập thành công")
                );
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body( Map.of("statusCode", ResponseCode.FALSE_USERNAME_OR_PASSWORD,
                    "message", "Tài khoản hoặc mật khẩu không chính xác!"));
        }
        return ResponseEntity.badRequest().body(
                Map.of("statusCode", ResponseCode.AUTHENTICATION_FALSE,
                        "message", "Xác thực không thành công!")
        );
    }


}
