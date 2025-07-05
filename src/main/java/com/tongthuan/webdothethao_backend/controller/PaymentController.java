package com.tongthuan.webdothethao_backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.response.PaymentResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.PaymentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/get-payment")
    public ResponseEntity<Optional<PaymentResponse>> getPaymentByOrderId(@RequestParam("orderId") String orderId) {
        if (orderId.equalsIgnoreCase("")) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(paymentService.findByOrderId(orderId).map(PaymentResponse::new));
    }

    //    @PutMapping("/updateCODPayment")
    //    public ResponseEntity<?> updatePaymentByOrderId(@RequestBody PaymentRequest paymentRequest)
    //    {
    //        boolean result = paymentService.updateCODPayment(paymentRequest);
    //        if(result)
    //            return ResponseEntity.ok().body("Cap nhat trang thai thanh toan thanh cong!");
    //        return ResponseEntity.badRequest().body("Co loi trong qua trinh xu ly");
    //    }

}
