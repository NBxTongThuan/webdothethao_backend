package com.tongthuan.webdothethao_backend.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.tongthuan.webdothethao_backend.service.JWTService;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> cookieHeaders = accessor.getNativeHeader("cookie");
            if (cookieHeaders != null) {
                for (String header : cookieHeaders) {
                    for (String part : header.split(";")) {
                        String token = part.trim().substring(4);
                        String username = jwtService.extractUsername(token);
                        UserDetails userDetail = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                        accessor.setUser(auth);
                    }
                }
            }
        }
        return message;
    }
}
