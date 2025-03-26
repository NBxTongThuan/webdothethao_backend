package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users") // Chữ thường
public class UsersController {

    @Autowired
    private UsersService usersServiceInterface;


    @GetMapping("/checkExistsByUserName")
    public boolean checkExistsByUserName(@RequestParam("userName") String userName)
    {
        return usersServiceInterface.checkExistsByUserName(userName);
    }

    @GetMapping("/checkExistsByEmail")
    public boolean checkExistsByEmail(@RequestParam("email") String email)
    {
        return usersServiceInterface.checkExistsByEmail(email);
    }

    @GetMapping("/allUser")
    public ResponseEntity<List<Users>> getAllUsers() { // Trả về List<Users>
        List<Users> users = usersServiceInterface.findAll();
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

}
