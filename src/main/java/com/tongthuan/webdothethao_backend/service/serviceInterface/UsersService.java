package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {
    public Page<Users> findAllUsersPage(Pageable pageable);
    public boolean checkExistsByUserName(String userName);
    public boolean checkExistsByEmail(String email);
    Users findByUserName(String userName);

}
