package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

// ─── REQUEST DTO ────────────────────────────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestBudgetDTO {

    private BigDecimal travelAmount;
    private BigDecimal accommodationAmount;
    private BigDecimal localTransportAmount;
    private BigDecimal mealsAmount;
    // subTotal, contingency, totalBudget calculated automatically
}
