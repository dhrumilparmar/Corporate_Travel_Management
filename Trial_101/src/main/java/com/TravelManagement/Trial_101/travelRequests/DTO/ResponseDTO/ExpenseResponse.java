// ExpenseResponse.java
package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ExpenseResponse {
    private Integer expenseId;
    private BigDecimal amount;
    private String categoryName;        // Better to return name instead of just ID
    private Integer categoryId;
    private LocalDate date;
    private String description;
    private String receipt;
    private LocalDateTime createdAt;
}