package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.request.AddAddressRequest;
import com.tongthuan.webdothethao_backend.dto.response.AddressResponse;
import com.tongthuan.webdothethao_backend.service.serviceInterface.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/getAddress")
    public ResponseEntity<List<AddressResponse>> findByUser(HttpServletRequest request)
    {
        return ResponseEntity.ok().body(addressService.findByUser(request).stream().map(AddressResponse::new).toList());
    }

    @PostMapping("/addAddress")
    public ResponseEntity<Boolean> addAddress(AddAddressRequest addAddressRequest, HttpServletRequest request)
    {
        boolean result = addressService.AddAddress(addAddressRequest,request);
        if(!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }



}
