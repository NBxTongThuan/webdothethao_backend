package com.tongthuan.webdothethao_backend.controller.publiccontroller;

import com.tongthuan.webdothethao_backend.constantvalue.ResponseCode;
import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.LoginRequest;
import com.tongthuan.webdothethao_backend.dto.response.UserResponse.UserInformationResponse;
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
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    JWTService jwtService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassWord()));
            if (authentication.isAuthenticated()) {

                Users user = usersService.findByUserName(loginRequest.getUserName());
                if (!user.isActive())
                    return ResponseEntity.ok().body(
                            Map.of("statusCode", ResponseCode.USER_NOT_ACTIVE,
                                    "message", "Tài khoản chưa được kích hoạt, vui lòng kích hoạt tài khoản trước!")
                    );

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
            return ResponseEntity.badRequest().body(Map.of("statusCode", ResponseCode.FALSE_USERNAME_OR_PASSWORD,
                    "message", "Sai tên đăng nhập hoặc mật khẩu!"));
        }
        return ResponseEntity.badRequest().body(Map.of("statusCode", ResponseCode.FALSE_USERNAME_OR_PASSWORD,
                "message", "Sai tên đăng nhập hoặc mật khẩu!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Xóa cookie
        response.addCookie(cookie);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/me")
    public ResponseEntity<UserInformationResponse> getCurrentUser(@CookieValue(name = "token") String token) {

        UserInformationResponse userInfo = new UserInformationResponse();
        userInfo.setUserName(jwtService.extractUsername(token));
        userInfo.setCartId(jwtService.extractCartId(token));
        userInfo.setRole(jwtService.extractRole(token));
        return ResponseEntity.ok(userInfo);
    }

}
