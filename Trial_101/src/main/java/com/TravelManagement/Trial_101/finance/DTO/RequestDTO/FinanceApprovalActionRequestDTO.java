package com.TravelManagement.Trial_101.finance.DTO.RequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// Sent when Finance Officer clicks APPROVE or REJECT button
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceApprovalActionRequestDTO {

    private Integer travelReqID;   // Which request
    private Integer approverID;    // Finance officer's employeeID
    private String  action;        // "APPROVED" or "REJECTED"
    private Integer employeeid;  //finaceid

}
