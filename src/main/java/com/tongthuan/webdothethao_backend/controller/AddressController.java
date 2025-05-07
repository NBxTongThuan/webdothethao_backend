package com.tongthuan.webdothethao_backend.controller;

import com.tongthuan.webdothethao_backend.dto.UpdateAddressRequest;
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

    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam("addressId")String addressId,HttpServletRequest request){
        boolean result = addressService.deleteAddress(addressId,request);
        if(!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/addAddress")
    public ResponseEntity<Boolean> addAddress(@RequestBody AddAddressRequest addAddressRequest, HttpServletRequest request)
    {
        boolean result = addressService.AddAddress(addAddressRequest,request);
        if(!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<Boolean> updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest, HttpServletRequest request)
    {
        boolean result = addressService.updateAddress(updateAddressRequest,request);
        if(!result)
            return ResponseEntity.badRequest().body(false);
        return ResponseEntity.ok(true);
    }



}
