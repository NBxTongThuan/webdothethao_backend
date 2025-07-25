package com.tongthuan.webdothethao_backend.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.OrderRequest;
import com.tongthuan.webdothethao_backend.service.VnPayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment/vn-pay")
@RequiredArgsConstructor
public class VNPayController {

    @Autowired
    private final VnPayService vnpayService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        try {
            String url = vnpayService.createPaymentUrl(orderRequest, request);
            return ResponseEntity.ok(Map.of("paymentUrl", url));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/re-create")
    public ResponseEntity<?> reCreatePayment(@RequestParam("paymentId") String paymentId) {
        try {
            String url = vnpayService.reCreatePaymentUrl(paymentId);
            return ResponseEntity.ok(Map.of("paymentUrl", url));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/return")
    public void handleVnpayReturn(HttpServletRequest request, HttpServletResponse response)
            throws NoSuchAlgorithmException, IOException {
        Map<String, String> vnpParams = new HashMap<>();

        // Lấy tất cả tham số từ URL
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements(); ) {
            String param = e.nextElement();
            vnpParams.put(param, request.getParameter(param));
        }

        //        return ResponseEntity.ok(vnpayService.handlePaymentReturn(vnpParams));
        String result = vnpayService.handlePaymentReturn(vnpParams);
        String redirectUrl = "http://localhost:3000/payment-return/" + (result);
        response.sendRedirect(redirectUrl);
    }
}
