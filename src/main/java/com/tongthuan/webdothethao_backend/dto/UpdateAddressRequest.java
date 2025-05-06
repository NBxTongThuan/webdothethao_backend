package com.tongthuan.webdothethao_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressRequest {

    private String addressId;
    private String toName;
    private String toPhone;
    private String toDistrict;
    private String toProvince;
    private String toWard;
    private String toAddress;

}
