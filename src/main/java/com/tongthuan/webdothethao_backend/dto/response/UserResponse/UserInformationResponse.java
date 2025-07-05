package com.tongthuan.webdothethao_backend.dto.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponse {

    private String userName;
    private String role;
    private String CartId;
}
