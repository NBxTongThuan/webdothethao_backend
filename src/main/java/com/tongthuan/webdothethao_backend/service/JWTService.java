package com.tongthuan.webdothethao_backend.service;

import com.tongthuan.webdothethao_backend.constantvalue.Role;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.CartRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CartService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService {

    @Autowired
    private UsersService usersService;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @Autowired
    private CartRepository cartRepository;

    //    Lấy token cho userName
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        Users user = usersService.findByUserName(userName);

        if (user != null) {
            claims.put("ROLE", user.getRole().name());
            claims.put("CartId", cartRepository.findCartByUserId(user.getUserId()).getCartId());
        }
        return createToken(claims, userName);

    }

    public String getTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    //    Tạo token dựa trên Map và userName
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .signWith(getSignKey())
                .compact();
    }

    //Lấy secret key
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Trích xuất thông tin
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    // Kiểm tra thời gian hết hạn từ JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //Lấy tên đăng nhập
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Kiểm tra Jwt hết hạn
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Kiểm tra tính hợp lệ
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //lấy ROLE
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("ROLE", String.class));
    }

    public String extractCartId(String token) {
        return extractClaim(token, claims -> claims.get("CartId", String.class));
    }

}
