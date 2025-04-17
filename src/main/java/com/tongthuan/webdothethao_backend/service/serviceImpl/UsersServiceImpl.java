package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;


    @Override
    public Page<Users> findAllUsersPage(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    @Override
    public boolean checkExistsByUserName(String userName) {
        return usersRepository.existsByUserName(userName);
    }

    @Override
    public boolean checkExistsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public Users findByUserName(String userName) {
        return usersRepository.findByUserName(userName).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepository.findByUserName(username).orElse(null);
        if(user == null)
        {
            throw new UsernameNotFoundException("Khong tim thay nguoi dung");
        }
        List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole().name()));
        User user1 = new User(user.getUserName(),user.getPassword(),roles);

        return user1;
    }
}
