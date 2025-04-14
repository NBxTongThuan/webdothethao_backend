package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.dto.request.UserDetailRequest;
import com.tongthuan.webdothethao_backend.entity.UserDetails;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.UserDetailsRepository;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Optional<UserDetails> findByUserName(String userName) {
        return Optional.of(userDetailsRepository.findByUserName(userName));
    }

    @Transactional
    @Override
    public UserDetails updateUserDetail(UserDetailRequest userDetailsRequest) {

        UserDetails userDetail = userDetailsRepository.findByUserDetailId(userDetailsRequest.getUserDetailId());
        if (userDetail == null) {
            return null;
        }
        userDetail.setUserDetailId(userDetailsRequest.getUserDetailId());
        Users user = usersRepository.findByUserName(userDetailsRequest.getUserName());
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
