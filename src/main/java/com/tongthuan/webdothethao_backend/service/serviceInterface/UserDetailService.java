package com.tongthuan.webdothethao_backend.service.serviceInterface;

import java.util.Optional;

import com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest.UserDetailRequest;
import com.tongthuan.webdothethao_backend.entity.UserDetail;

public interface UserDetailService {

    public Optional<UserDetail> findByUserName(String userName);

    UserDetail updateUserDetail(UserDetailRequest userDetailsRequest);
}
