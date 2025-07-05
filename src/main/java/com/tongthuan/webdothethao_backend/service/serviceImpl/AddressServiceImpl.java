package com.tongthuan.webdothethao_backend.service.serviceImpl;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tongthuan.webdothethao_backend.dto.UpdateAddressRequest;
import com.tongthuan.webdothethao_backend.dto.request.AddAddressRequest;
import com.tongthuan.webdothethao_backend.entity.Address;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.AddressRepository;
import com.tongthuan.webdothethao_backend.service.JWTService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.AddressService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public boolean AddAddress(AddAddressRequest addAddressRequest, HttpServletRequest request) {

        String token = jwtService.getTokenFromCookie(request);

        if (token.equalsIgnoreCase("")) return false;

        if (jwtService.isTokenExpired(token)) return false;

        Users user = usersService.findByUserName(jwtService.extractUsername(token));
        if (user == null) {
            return false;
        }

        Address address = new Address();
        address.setToAddress(addAddressRequest.getToAddress());
        address.setToPhone(addAddressRequest.getToPhone());
        address.setToName(addAddressRequest.getToName());
        address.setToDistrict(addAddressRequest.getToDistrict());
        address.setToProvince(addAddressRequest.getToProvince());
        address.setToWard(addAddressRequest.getToWard());
        address.setUser(user);

        addressRepository.saveAndFlush(address);
        return true;
    }

    @Override
    public boolean updateAddress(UpdateAddressRequest updateAddressRequest, HttpServletRequest request) {
        String token = jwtService.getTokenFromCookie(request);

        if (token.equalsIgnoreCase("")) return false;

        if (jwtService.isTokenExpired(token)) return false;

        Users user = usersService.findByUserName(jwtService.extractUsername(token));
        if (user == null) {
            return false;
        }

        Address address =
                addressRepository.findById(updateAddressRequest.getAddressId()).orElse(null);
        if (address == null) return false;

        address.setToName(updateAddressRequest.getToName());
        address.setToProvince(updateAddressRequest.getToProvince());
        address.setToAddress(updateAddressRequest.getToAddress());
        address.setToPhone(updateAddressRequest.getToPhone());
        address.setToWard(updateAddressRequest.getToWard());
        address.setToDistrict(updateAddressRequest.getToDistrict());
        addressRepository.saveAndFlush(address);
        return true;
    }

    @Override
    public List<Address> findByUser(HttpServletRequest request) {

        String token = jwtService.getTokenFromCookie(request);
        if (jwtService.isTokenExpired(token)) return null;

        if (token.equalsIgnoreCase("")) return null;
        Users user = usersService.findByUserName(jwtService.extractUsername(token));
        if (user == null) {
            return null;
        }

        return addressRepository.findByUserName(user.getUserName());
    }

    @Override
    public boolean deleteAddress(String addressId, HttpServletRequest request) {
        String token = jwtService.getTokenFromCookie(request);

        if (token.equalsIgnoreCase("")) return false;

        if (jwtService.isTokenExpired(token)) return false;

        Address address = addressRepository.findById(addressId).orElse(null);
        if (address == null) return false;
        addressRepository.deleteById(addressId);
        return true;
    }
}
