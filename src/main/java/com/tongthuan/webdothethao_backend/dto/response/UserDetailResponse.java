package com.tongthuan.webdothethao_backend.dto.response;

import java.sql.Date;

import com.tongthuan.webdothethao_backend.constantvalue.Gender;
import com.tongthuan.webdothethao_backend.entity.UserDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private String userDetailId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String address;

    public UserDetailResponse(UserDetail userDetails) {
        this.userDetailId = userDetails.getUserDetailId();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.gender = userDetails.getGender();
        this.dateOfBirth = userDetails.getDateOfBirth();
        this.phoneNumber = userDetails.getPhoneNumber();
        this.province = userDetails.getProvince();
        this.district = userDetails.getDistrict();
        this.ward = userDetails.getWard();
        this.address = userDetails.getAddress();
    }
}
