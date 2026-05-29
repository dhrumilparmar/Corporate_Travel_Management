package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelRequestFinanceDTO {

    private Integer travelCode;
    private String travelReqID;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalBudget;
    private String status;
    private Integer employeeID;
}
