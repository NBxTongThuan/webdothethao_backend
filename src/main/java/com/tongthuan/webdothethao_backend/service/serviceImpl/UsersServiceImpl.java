package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;


    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }
}
