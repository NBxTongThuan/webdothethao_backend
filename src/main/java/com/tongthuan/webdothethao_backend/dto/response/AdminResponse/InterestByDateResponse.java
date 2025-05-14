package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestByDateResponse {
    private LocalDate date;
    private Long total;
}
