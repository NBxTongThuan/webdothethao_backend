package com.tongthuan.webdothethao_backend.service.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.TopBuyerResponse;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.UserStatsResponse;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;

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
    public UserStatsResponse getUserStats() {
        LocalDate now = LocalDate.now();

        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        LocalDate lastMonthDate = now.minusMonths(1);
        int lastMonth = lastMonthDate.getMonthValue();
        int lastYear = lastMonthDate.getYear();

        Long currentTotal = usersRepository.countByMonthAndYear(currentMonth, currentYear);
        Long total = usersRepository.countAll();

        long lastMonthTotal = total - currentTotal;

        double percentChange = 0.0;
        if (lastMonthTotal != 0) {
            percentChange = ((double) (total - lastMonthTotal) / lastMonthTotal) * 100;
        } else if (currentTotal > 0) {
            percentChange = 100.0;
        }

        return new UserStatsResponse(total, lastMonthTotal, percentChange);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersRepository.findByUserName(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("Khong tim thay nguoi dung");
        }
        List<GrantedAuthority> roles =
                List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new User(user.getUserName(), user.getPassword(), roles);
    }

    @Override
    public Page<TopBuyerResponse> findTopBuyer(Pageable pageable) {
        Page<Object[]> result = usersRepository.findTopBuyer(pageable);

        List<TopBuyerResponse> content = result.stream()
                .map(row -> new TopBuyerResponse((Users) row[0], (Long) row[1]))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, result.getTotalElements());
    }
}
