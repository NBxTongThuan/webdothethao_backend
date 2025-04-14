package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.request.UserDetailRequest;
import com.tongthuan.webdothethao_backend.entity.UserDetails;

import java.util.Optional;

public interface UserDetailService {

    public Optional<UserDetails> findByUserName(String userName);

    UserDetails updateUserDetail(UserDetailRequest userDetailsRequest);
}
