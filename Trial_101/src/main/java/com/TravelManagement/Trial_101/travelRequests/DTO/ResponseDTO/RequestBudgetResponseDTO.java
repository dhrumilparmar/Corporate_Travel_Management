package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestBudgetResponseDTO {

    private Integer    budgetID;
    private BigDecimal travelAmount;
    private BigDecimal accommodationAmount;
    private BigDecimal localTransportAmount;
    private BigDecimal mealsAmount;
    private BigDecimal subTotal;        // auto calculated
    private BigDecimal contingency;     // subTotal * 5%
    private BigDecimal totalBudget;     // subTotal * 1.05
}
