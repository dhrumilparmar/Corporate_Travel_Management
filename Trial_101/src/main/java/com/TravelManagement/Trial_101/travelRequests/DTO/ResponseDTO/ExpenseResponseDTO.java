package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDTO {

    private Integer    expenseID;
    private Integer travelReqID;
    private String requestCode;
    private Integer EmployeeID;
    private String EmployeeName;
    private Integer CategoryID;
    private String CategoryName;
    private String     description;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String     receiptFile;
    private String     status;
    private LocalDateTime createdAt;

    // Nested
//    private EmployeeSummaryDTO          employee;
//    private ExpenseCategoryResponseDTO  category;
//    private TravelRequestSummaryDTO     travelRequest;
}
