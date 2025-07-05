package com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    private String userName;
    private String oldPassword;
    private String newPassword;
}
