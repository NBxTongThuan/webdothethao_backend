package com.tongthuan.webdothethao_backend.service.serviceInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.TopBuyerResponse;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.UserStatsResponse;
import com.tongthuan.webdothethao_backend.entity.Users;

public interface UsersService extends UserDetailsService {
    public Page<Users> findAllUsersPage(Pageable pageable);

    public boolean checkExistsByUserName(String userName);

    public boolean checkExistsByEmail(String email);

    Users findByUserName(String userName);

    // admin
    public UserStatsResponse getUserStats();

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Page<TopBuyerResponse> findTopBuyer(Pageable pageable);
}
