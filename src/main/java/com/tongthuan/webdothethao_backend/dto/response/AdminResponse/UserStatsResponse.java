package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsResponse {
    private Long currentMonthTotal;
    private Long lastMonthTotal;
    private Double percentChange;
}