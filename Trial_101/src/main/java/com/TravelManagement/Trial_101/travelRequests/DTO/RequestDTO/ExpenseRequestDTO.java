package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import com.TravelManagement.Trial_101.travelRequests.Entity.ExpenseCategory;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
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

    private Integer travelReqID; // Use Integer ID, NOT Entity
    private Integer employeeID;
    private Integer categoryID;
    private String description;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String receiptFile;

}
