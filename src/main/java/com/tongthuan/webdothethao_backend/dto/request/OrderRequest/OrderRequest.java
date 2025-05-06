package com.tongthuan.webdothethao_backend.dto.request.OrderRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String orderNote;
    private String toAddress;
    private String toProvince;
    private String toDistrict;
    private String toWard;
    private String toPhone;
    private String toName;
}
