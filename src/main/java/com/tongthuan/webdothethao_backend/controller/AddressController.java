package com.tongthuan.webdothethao_backend.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tongthuan.webdothethao_backend.dto.UpdateAddressRequest;
import com.tongthuan.webdothethao_backend.dto.request.AddAddressRequest;
import com.tongthuan.webdothethao_backend.dto.response.AddressResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.AddressService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/my-address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/get")
    public ResponseEntity<List<AddressResponse>> findByUser(HttpServletRequest request) {
        return ResponseEntity.ok()
                .body(addressService.findByUser(request).stream()
                        .map(AddressResponse::new)
                        .toList());
    }

    @DeleteMapping("/delete-by-id")
    public ResponseEntity<Boolean> deleteById(@RequestParam("addressId") String addressId, HttpServletRequest request) {
        boolean result = addressService.deleteAddress(addressId, request);
        if (!result) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addAddress(
            @RequestBody AddAddressRequest addAddressRequest, HttpServletRequest request) {
        boolean result = addressService.AddAddress(addAddressRequest, request);
        if (!result) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateAddress(
            @RequestBody UpdateAddressRequest updateAddressRequest, HttpServletRequest request) {
        boolean result = addressService.updateAddress(updateAddressRequest, request);
        if (!result) return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }
}
