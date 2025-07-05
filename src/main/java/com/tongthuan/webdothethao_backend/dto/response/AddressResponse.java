package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private String addressId;
    private String toName;
    private String toPhone;
    private String toDistrict;
    private String toProvince;
    private String toWard;
    private String toAddress;

    public AddressResponse(Address address) {

        this.addressId = address.getAddressId();
        this.toName = address.getToName();
        this.toPhone = address.getToPhone();
        this.toDistrict = address.getToDistrict();
        this.toProvince = address.getToProvince();
        this.toWard = address.getToWard();
        this.toAddress = address.getToAddress();
    }
}
