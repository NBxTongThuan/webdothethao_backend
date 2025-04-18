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
            "/api/reviews/getListReviews",
            "/api/cart/getListCartItem",
            "/api/provinces",
            "/api/districts",
            "/api/wards",
            "api/users/allUser",
            "/api/userDetail/getUserDetailByUserName",
            "/api/orders/myOrders",
            "/api/orderItems/listOrderItem",
            "/api/payment/getPayment",
            "/api/account/forgotPassword",
            "/api/orders/getOrderByOrderId",
            "api/reviews/seeAReview"
    };


    public static final String[] PUBLIC_DELETE_ENDPOINTS = {
            "/api/account/lockAccount"
    };

    public static final String[] CUSTOMER_PUT_ENDPOINTS = {
            "/api/userDetail/updateUserDetail",
            "/api/payment/updatePayment",
            "/api/orders/admin/updateOrder",
            "/api/account/changePassword",
            "/api/orders/cancelOrder",
            "/api/reviews/updateReview"
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/account/Register",
            "/api/account/Login",
            "/api/account/resetPassword",
            "/api/admin/account/Login",
    };

    public static final String[] CUSTOMER_POST_ENDPOINTS = {
            "/api/cart/addToCart",
            "/api/orders/codOrder",
            "/api/reviews/addReview",


    };

    public static final String[] CUSTOMER_DELETE_ENDPOINTS = {
            "/api/cart/deleteCartItem"
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/admin/getAllOrder",
            "/api/admin/products/getAllProduct",
            "/api/admin/productAttribute/getProductAttributeByProductId",
            "/api/admin/brands/getAll",
            "/api/admin/reviews/getAllReviews",
            "/api/admin/types/getAllType",
            "/api/admin/categories/getAllCategory",
            "/api/admin/categories/checkCategoryExists"

    };

    public static final String[] ADMIN_PUT_ENDPOINTS = {
            "/api/admin/orders/updateOrder",
            "/api/admin/categories/updateCategory",
            "/api/admin/categories/enableCategory"
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/api/admin/products/addProduct",

            "/api/admin/categories/addCategory"
    };

    public static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/api/admin/categories/deleteCategory"
    };

}
