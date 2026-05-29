package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;

import com.TravelManagement.Trial_101.employee.Entity.Employee;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseItemRequest {
    private BigDecimal amount;
//    private Employee employeeid;
    private Integer categoryId;           // matches your frontend "categoryID"
    private LocalDate date;
    private String description;
    private Boolean receipt = false;
}
