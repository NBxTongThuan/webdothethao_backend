package com.tongthuan.webdothethao_backend.dto.request.UserAccountRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String forgotPasswordCode;
    private String newPassword;

}
