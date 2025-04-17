package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.UserDetailRequest;
import com.tongthuan.webdothethao_backend.entity.UserDetail;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.UserDetailsRepository;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailService{

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Optional<UserDetail> findByUserName(String userName) {
        return Optional.of(userDetailsRepository.findByUserName(userName));
    }

    @Transactional
    @Override
    public UserDetail updateUserDetail(UserDetailRequest userDetailsRequest) {

        UserDetail userDetail = userDetailsRepository.findByUserDetailId(userDetailsRequest.getUserDetailId());
        if (userDetail == null) {
            return null;
        }
        userDetail.setUserDetailId(userDetailsRequest.getUserDetailId());
        Users user = usersRepository.findByUserName(userDetailsRequest.getUserName()).orElse(null);
        if (user == null) {
            return null;
        }
        userDetail.setUser(user);
        userDetail.setDateOfBirth(userDetailsRequest.getDateOfBirth());
        userDetail.setFirstName(userDetailsRequest.getFirstName());
        userDetail.setLastName(userDetailsRequest.getLastName());
        userDetail.setAddress(userDetailsRequest.getAddress());
        userDetail.setPhoneNumber(userDetailsRequest.getPhoneNumber());
        userDetail.setProvince(userDetailsRequest.getProvince());
        userDetail.setWard(userDetailsRequest.getWard());
        userDetail.setDistrict(userDetailsRequest.getDistrict());
        userDetail.setGender(userDetailsRequest.getGender());
        userDetailsRepository.saveAndFlush(userDetail);
        return userDetail;
    }

}
