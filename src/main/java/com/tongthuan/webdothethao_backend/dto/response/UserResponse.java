package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String username;
    private String email;
    private String role;
    private boolean active;
    private Date createdDate;
    private boolean enable;

    public UserResponse(Users user) {
        this.userId=user.getUserId();
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.active = user.isActive();
        this.createdDate = user.getCreatedDate();
        this.enable = user.isEnable();
    }
}
