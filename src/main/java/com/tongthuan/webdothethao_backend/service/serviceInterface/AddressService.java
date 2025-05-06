package com.tongthuan.webdothethao_backend.service.serviceInterface;

import com.tongthuan.webdothethao_backend.dto.UpdateAddressRequest;
import com.tongthuan.webdothethao_backend.dto.request.AddAddressRequest;
import com.tongthuan.webdothethao_backend.entity.Address;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AddressService {

    boolean AddAddress(AddAddressRequest addAddressRequest, HttpServletRequest request);

    boolean updateAddress(UpdateAddressRequest updateAddressRequest,HttpServletRequest request);

    List<Address> findByUser(HttpServletRequest request);

    boolean deleteAddress(String addressId, HttpServletRequest request);

}
