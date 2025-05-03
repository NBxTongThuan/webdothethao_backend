package com.tongthuan.webdothethao_backend.service;

import com.tongthuan.webdothethao_backend.config.VNPayConfig;
import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentMethod;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.OrderRequest;
import com.tongthuan.webdothethao_backend.entity.*;
import com.tongthuan.webdothethao_backend.repository.*;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VnPayService {


    @Autowired
    private final CartItemsRepository cartItemsRepository;

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final JWTService jwtService;

    @Autowired
    private final VNPayConfig vnpayConfig;

    @Autowired
    private final PaymentsRepository paymentRepo;

    @Autowired
    private final OrdersService ordersService;

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final OrdersRepository ordersRepository;

    public String createPaymentUrl(OrderRequest orderRequest, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String vnpTxnRef = "VNP_" + System.currentTimeMillis();
        String orderInfo = "Thanh toan don hang " + vnpTxnRef;

        String token = jwtService.getTokenFromCookie(request);
        if (token.equalsIgnoreCase(""))
            return "";


        String userName = jwtService.extractUsername(token);
        Users user = usersRepository.findByUserName(userName).orElse(null);
        if (user == null) {
            return "";
        }

        Cart cart = cartRepository.findCartByUserId(user.getUserId());
        if (cart == null)
            return "";

        Orders order = ordersService.createVNPayOrder(orderRequest, user, cart, vnpTxnRef);
        if (order == null)
            return "";

        // 2. Tạo param gửi VNPAY
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf((long) (order.getFinalPrice() * 100)));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        vnpParams.put("vnp_IpAddr", "127.0.0.1");
        vnpParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // 3. Sort & build query
        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String value = vnpParams.get(fieldName);
            if (value != null && !value.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            }
        }

        // Bỏ ký tự & cuối cùng
        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        // 4. Tính secure hash
        String secureHash = hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        // 5. Return URL
        return vnpayConfig.getVnpUrl() + "?" + query;
    }

    public String reCreatePaymentUrl(String paymentId) throws NoSuchAlgorithmException {

        String vnpTxnRef = "VNP_" + System.currentTimeMillis();
        String orderInfo = "Thanh toan don hang " + vnpTxnRef;

        Payments payment = paymentRepo.findById(paymentId).orElse(null);
        if (payment == null)
            return "";

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Payment already completed.");
        }

        payment.setVnpTxnRef(vnpTxnRef);
        paymentRepo.saveAndFlush(payment);

        // 2. Tạo param gửi VNPAY
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf((long) (payment.getAmount() * 100)));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        vnpParams.put("vnp_IpAddr", "127.0.0.1"); // hoặc lấy từ request
        vnpParams.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        // 3. Sort & build query
        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String value = vnpParams.get(fieldName);
            if (value != null && !value.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
                query.append(fieldName).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            }
        }

        // Bỏ ký tự & cuối cùng
        hashData.setLength(hashData.length() - 1);
        query.setLength(query.length() - 1);

        // 4. Tính secure hash
        String secureHash = hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);

        // 5. Return URL
        return vnpayConfig.getVnpUrl() + "?" + query;

    }

    private String hmacSHA512(String key, String data) throws NoSuchAlgorithmException {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC SHA512", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    public long getFinalPrice(String cartId) {
        long finalPrice = 0;
        List<CartItems> cartItems = cartItemsRepository.findByCartId(cartId);
        if (!cartItems.isEmpty()) {
            System.out.println("cart item !null");
            for (CartItems cartItem : cartItems) {
                Products product = cartItem.getProductAttribute().getProduct();
                System.out.println("product money off:" + product.getMoneyOff());
                System.out.println("product price " + product.getPrice());
                System.out.println("product name" + product.getProductName());
                finalPrice += product.getMoneyOff() > 0 ? ((product.getPrice() - product.getMoneyOff()) * cartItem.getQuantity()) : (product.getPrice() * cartItem.getQuantity());
            }
        }
        return finalPrice;
    }

    public String handlePaymentReturn(Map<String, String> vnpParams) throws NoSuchAlgorithmException {
        String secureHash = vnpParams.remove("vnp_SecureHash");
        String secureHashType = vnpParams.remove("vnp_SecureHashType");

        // 1. Tính lại hash để verify
        List<String> keys = new ArrayList<>(vnpParams.keySet());
        Collections.sort(keys);

        StringBuilder hashData = new StringBuilder();
        for (String key : keys) {
            String value = vnpParams.get(key);
            if (value != null && !value.isEmpty()) {
                hashData.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append('&');
            }
        }
        hashData.setLength(hashData.length() - 1); // xóa & cuối

        String myHash = hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());

        if (!secureHash.equalsIgnoreCase(myHash)) {
            return "Sai checksum, từ chối giao dịch!";
        }

        // 2. Check trạng thái giao dịch
        String responseCode = vnpParams.get("vnp_ResponseCode");
        String vnpTxnRef = vnpParams.get("vnp_TxnRef");

        Optional<Payments> optionalPayment = paymentRepo.findByVnpTxnRef(vnpTxnRef);

        if (optionalPayment.isEmpty()) {
            return "Không tìm thấy giao dịch!";
        }

        Payments payment = optionalPayment.get();

        if (!(payment.getStatus() == PaymentStatus.PENDING)) {
            return "Giao dịch đã được xử lý trước đó!";
        }

        if (responseCode.equals("00")) {
            // Thành công → cập nhật trạng thái + tạo order
            payment.setStatus(PaymentStatus.COMPLETED);
            Orders order = payment.getOrder();
            order.setStatus(OrderStatus.CONFIRMED);
            ordersRepository.saveAndFlush(order);
            paymentRepo.save(payment);

            return "success";
        } else {
            ordersService.handleCancelVNPayOrder(payment);
            return "error";
        }
    }

}
