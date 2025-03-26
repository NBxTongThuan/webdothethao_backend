package com.tongthuan.webdothethao_backend.security;

import com.tongthuan.webdothethao_backend.filter.JwtFilter;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UsersService userService)
    {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(userService);
        dap.setPasswordEncoder(passwordEncoder());

        return dap;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(
                configurer -> configurer
                        .requestMatchers(HttpMethod.GET, EndPoints.PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.POST, EndPoints.PUBLIC_POST_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, EndPoints.ADMIN_GET_ENDPOINTS).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, EndPoints.ADMIN_POST_ENDPOINTS).hasAuthority("ADMIN")
                        .anyRequest().authenticated()
        );

        httpSecurity.cors(
                cors -> {
                    cors.configurationSource(
                            request -> {
                                CorsConfiguration configuration = new CorsConfiguration();
                                configuration.addAllowedOrigin(EndPoints.fe_url);
                                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
                                configuration.addAllowedHeader("*");
                                configuration.setAllowCredentials(true);
                                return configuration;
                            });
                });
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();

    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    {
        try {
                return authenticationConfiguration.getAuthenticationManager();
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


}
