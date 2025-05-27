package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountOrderStatusResponse {
    public long orderCancel;
    public long orderDelivered;
}
