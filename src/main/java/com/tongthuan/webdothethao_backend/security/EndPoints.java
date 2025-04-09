package com.tongthuan.webdothethao_backend.security;

public class EndPoints {
    public static final String fe_url = "http://localhost:3000";

    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/users/checkExistsByUserName",
            "/api/users/checkExistsByEmail",
            "/api/account/Active",
            "/api/brands/getBrandByProductId",
            "/api/categories",
            "/api/images/getListImages",
            "/api/images/getFirstImage",
            "/api/productAttribute",
            "/api/products",
            "/api/products/listByCateId",
            "/api/products/listByName",
            "/api/products/oneproduct",
            "/api/reviews",
            "/api/cart/getCartIDByUserName",
            "/api/cart/getListCartItem"

    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/account/Register",
            "/api/account/Login",
    };

    public static final String[] CUSTOMER_POST_ENDPOINTS = {
            "/api/cart/addToCart"
    };

    public static final String[] CUSTOMER_DELETE_ENDPOINTS = {
            "/api/cart/deleteCartItem"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {

    };

    public static final String[] ADMIN_POST_ENDPOINTS = {

    };

}
