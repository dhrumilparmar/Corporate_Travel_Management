package com.TravelManagement.Trial_101.finance.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

// Renders EACH ROW in the pending approvals table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancePendingCardDTO {

    // Column 1: REQ ID
    private Integer    travelReqID;
    private String     requestCode;        // #TR-1042

    // Column 2: EMPLOYEE
    private Integer employeeid;
    private String     employeeName;       // Jonathan Sterling
//    private String     employeeInitials;   // "JS" (for the avatar circle)
//    private String     designationName;    // Strategic Operations
    private String     departmentName;

    // Column 3: BUDGET AMOUNT
    private BigDecimal budgetAmount;       // $4,250.00
    private String     currency;           // USD

    // Column 4: POLICY COMPLIANCE
    private Boolean    policyViolation;    // false = "Within Policy", true = "Policy Violation"
    private String     policyStatus;       // "Within Policy" / "Policy Violation"

    // Column 5: MANAGER APPROVAL
    private String     managerApprovalStatus; // "Manager Approved"
//    private String     managerName;           // Who approved it

    // Column 6: ACTIONS (buttons)
    private String     status;             // MANAGER_APPROVED
}