package com.TravelManagement.Trial_101.manager.DTO.RequestDTO;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalActionRequestDTO {

    private Integer travelReqID;  // Which request is being approved/rejected
    private Integer approverID;   // Who is approving (Manager's employeeID)
    private String  action;       // "APPROVED" or "REJECTED"
    private String  remarks;      // Comments/Notes from manager
    private Integer employeeid;  //employee id
}
