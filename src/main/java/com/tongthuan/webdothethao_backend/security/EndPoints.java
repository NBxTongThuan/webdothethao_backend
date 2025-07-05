package com.tongthuan.webdothethao_backend.security;

public class EndPoints {
    public static final String fe_url = "http://localhost:3000";

    public static final String[] PUBLIC_GET_ENDPOINTS = {
        // VNPay
        "/api/payment/vn-pay/return",

        // VN Address
        "/api/vn/address/provinces",
        "/api/vn/address/districts",
        "/api/vn/address/wards",

        // Brands
        "/api/brands/get-by-product-id",

        // Categories
        "/api/categories",
        "/api/categories/top-4",

        // Image
        "/api/images/get-all-by-product-id",
        "/api/images/first-image-of-product",

        // ProductAttribute
        "/api/product-attribute",

        // Products
        "/api/products/get-all",
        "/api/products/page-by-category-id",
        "/api/products/page-by-name",
        "/api/products/get-by-id",
        "/api/products/top-4",
        "/api/products/page-newest",
        "/api/products/same",
        "/api/products/discounting",
        "/api/products/get-by-cate-price",
        "/api/products/get-by-price",
        // Reviews
        "/api/reviews/get-list-by-product-id",

        // Account
        "/api/account/forgot-password",
        "/api/account/active",

        // User
        "/api/users/check-exists-by-user-name",
        "/api/users/check-exists-by-email",

        // Messages
        "/api/chat/history",
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {

        // Admin
        "/api/admin/auth/login",

        // Users
        "/api/auth/login",

        // Account
        "/api/account/register",
        "/api/account/reset-password",

        // Auth
        "/api/auth/logout",
    };

    public static final String[] PUBLIC_PUT_ENDPOINTS = {

        // Account
        "/api/account/change-password",
    };

    public static final String[] CUSTOMER_GET_ENDPOINTS = {

        // Reviews
        "/api/reviews/see-review",

        // MyAddress
        "/api/my-address/get",

        // Cart
        "/api/cart/get-list",

        // OrderItems
        "/api/order-items/list",

        // Orders
        "/api/orders/my-orders",
        "/api/orders/get-by-id",

        // Payment
        "/api/payment/get-payment",

        // UserDetail
        "/api/user-detail/get-by-name"
    };

    public static final String[] CUSTOMER_POST_ENDPOINTS = {
        // VNPay
        "/api/payment/vn-pay/create",
        "/api/payment/vn-pay/re-create",

        // Reviews
        "/api/reviews/add",

        // MyAddress
        "/api/my-address/add",

        // Cart
        "/api/cart/add",

        // Orders
        "/api/orders/cod-order",
    };

    public static final String[] CUSTOMER_PUT_ENDPOINTS = {

        // Reviews
        "/api/reviews/update",

        // MyAddress
        "/api/my-address/update",

        // CartItem
        "/api/cart-item/update-quantity",

        // Orders
        "/api/orders/cancel",

        // UserDetail
        "/api/user-detail/update",
    };

    public static final String[] CUSTOMER_DELETE_ENDPOINTS = {
        // MyAddress
        "/api/my-address/delete-by-id",

        // Cart
        "/api/cart/delete-cart-item",
    };

    public static final String[] HAS_ROLE_POST_ENDPOINTS = {
        // Auth

    };

    public static final String[] HAS_ROLE_GET_ENDPOINTS = {
        // Auth
        "/api/auth/me",
    };

    public static final String[] ADMIN_GET_ENDPOINTS = {

        // Brands
        "/api/admin/brands/get-all",
        "/api/admin/brands/get-page",

        // Categories
        "/api/admin/categories/find-all",
        "/api/admin/categories/get-by-id",
        "/api/admin/categories/get-by-name",
        "/api/admin/categories/get-page",
        "/api/admin/categories/check-exists",

        // Images
        "/api/admin/image/get-by-product-id",
        "/api/admin/image/first-image-of-product",

        // Notifications
        "/api/admin/notifications/get-unread",
        "/api/admin/notifications/get-all",

        // Orders
        "/api/admin/orders/get-all",
        "/api/admin/orders/total-today",
        "/api/admin/orders/revenue-of-month",
        "/api/admin/orders/revenue-by-date",
        "/api/admin/orders/interest-by-date",
        "/api/admin/orders/new",
        "/api/admin/orders/get-by-id",
        "/api/admin/orders/get-rate-by-status",

        // OrderItem
        "/api/admin/order-items/list",

        // ProductAttribute
        "/api/admin/product-attribute/get-page-by-product-id",

        // Products
        "/api/admin/products/get-page",
        "/api/admin/products/get-page-discount",
        "/api/admin/products/get-count-enable",
        "/api/admin/products/get-enable",
        "/api/admin/products/get-by-id",
        "/api/admin/products/get-top-sale",
        "/api/admin/products/get-top-slow-sale",

        // Reviews
        "/api/admin/reviews/get-page",

        // Types
        "/api/admin/types/get-page",
        "/api/admin/types/get-by-category-name",
        "/api/admin/types/check-exists",

        // Users
        "/api/admin/users/all",
        "/api/admin/users/stats",
        "/api/admin/users/top-buyer",

        // UserDetail
        "/api/admin/user-detail/get-by-name"
    };

    public static final String[] ADMIN_PUT_ENDPOINTS = {

        // Category
        "/api/admin/categories/enable",
        "/api/admin/categories/update",

        // Notification
        "/api/admin/notifications/set-read",

        // Order
        "api/admin/orders/update",

        // ProductAttribute
        "/api/admin/product-attribute/update",
        "/api/admin/product-attribute/enable",

        // Products
        "/api/admin/products/update-discount",
        "/api/admin/products/stop-discount",
        "/api/admin/products/enable",
        "/api/admin/products/update",

        // Types
        "/api/admin/types/enable",
        "/api/admin/types/update",

        // Account
        "/api/admin/account/unLock-account",
    };

    public static final String[] ADMIN_POST_ENDPOINTS = {

        // Category
        "/api/admin/categories/add",

        // ProductAttribute
        "/api/admin/product-attribute/add",

        // Products
        "/api/admin/products/add",
        "/api/admin/products/check-exists",

        // Types
        "/api/admin/types/add",
    };

    public static final String[] ADMIN_DELETE_ENDPOINTS = {

        // Category
        "/api/admin/categories/disable",

        // ProductAttribute
        "/api/admin/product-attribute/disable",

        // Products
        "/api/admin/products/disable",

        // Types
        "/api/admin/types/disable",

        // Account
        "/api/admin/account/lock-account",
    };
}
