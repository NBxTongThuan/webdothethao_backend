package com.tongthuan.webdothethao_backend.filter;

import com.tongthuan.webdothethao_backend.service.JWTService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter{

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsersService usersService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName = jwtService.extractUsername(token);
        }
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = usersService.loadUserByUsername(userName);
            if(jwtService.validateToken(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        System.out.println("Token: " + token + " | Path: " + request.getRequestURI());
        filterChain.doFilter(request, response);


    }
}
