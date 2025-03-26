package com.tongthuan.webdothethao_backend.security;

public class EndPoints {
    public static final String fe_url = "http://localhost:3000";

    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/users/checkExistsByUserName",
            "/api/users/checkExistsByEmail",
            "/api/account/Active"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/account/Register"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {

    };

    public static final String[] ADMIN_POST_ENDPOINTS = {

    };

}
