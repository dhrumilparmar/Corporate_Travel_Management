package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// ─── REQUEST DTO (Submit Expense) ───────────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequestDTO {

    private Integer    travelReqID;
    private Integer    employeeID;
    private Integer    categoryID;
    private String     description;
    private BigDecimal amount;
    private LocalDate  expenseDate;
    private String     receiptFile;   // file path or URL
}
