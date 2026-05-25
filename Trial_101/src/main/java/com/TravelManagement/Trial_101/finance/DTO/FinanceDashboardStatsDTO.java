package com.TravelManagement.Trial_101.finance.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

// Renders the 4 stat cards at the top of the page
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceDashboardStatsDTO {

    // Header
    private Long       totalPendingCount;    // "14 pending requests"
    private BigDecimal totalPendingValue;    // "$158,420.00" (top right)

    // Stat Card 1
    private Long       awaitingValidation;  // "14"

    // Stat Card 3
    private Long       highValueCount;      // "3" (requests > $10,000)
}