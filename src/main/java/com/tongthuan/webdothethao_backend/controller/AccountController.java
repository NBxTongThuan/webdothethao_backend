package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.service.AccountService;
import com.tongthuan.webdothethao_backend.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    JWTService jwtService;

    @PostMapping("/Register")
    public ResponseEntity<?> registerNewAccount(@Validated @RequestBody Users user)
    {
        ResponseEntity<?> response = accountService.registerAccount(user);
        return  response;
    }

    @GetMapping("/Active")
    public ResponseEntity<?> activeAccount(@RequestParam("email") String email,@RequestParam("activeCode") String activeCode)
    {
        ResponseEntity<?> response = accountService.activeAccount(email, activeCode);
        return response;
    }





}
