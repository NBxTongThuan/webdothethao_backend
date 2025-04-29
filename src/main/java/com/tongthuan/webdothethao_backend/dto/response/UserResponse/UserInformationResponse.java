package com.tongthuan.webdothethao_backend.dto.response.UserResponse;

import com.tongthuan.webdothethao_backend.constantvalue.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponse {

    private String userName;
    private String role;
    private String CartId;

}
