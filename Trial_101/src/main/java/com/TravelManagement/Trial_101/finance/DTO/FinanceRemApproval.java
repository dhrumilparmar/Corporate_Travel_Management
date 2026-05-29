package com.TravelManagement.Trial_101.finance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FinanceRemApproval {
    private Integer employeeId;
    private String status;
    private Integer travelReqID;
    private String destination;
    private String requestCode;
    private BigDecimal totalBudget;

}
