package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;

import com.TravelManagement.Trial_101.employee.Entity.Employee;
import lombok.Data;
import java.util.List;

@Data
public class ExpenseCreateRequest {
    private Integer travelRequestId;           // Very important
    private List<ExpenseItemRequest> expenses;
    private Integer employeeId;
}