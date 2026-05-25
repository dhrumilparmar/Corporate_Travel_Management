package com.TravelManagement.Trial_101.finance.Service.Impl;

import com.TravelManagement.Trial_101.employee.Entity.Employee;
import com.TravelManagement.Trial_101.finance.DTO.FinanceApprovalActionResponseDTO;
import com.TravelManagement.Trial_101.finance.DTO.FinancePendingCardDTO;
import com.TravelManagement.Trial_101.finance.DTO.RequestDTO.FinanceApprovalActionRequestDTO;
import com.TravelManagement.Trial_101.finance.Repository.financeRepo;
import com.TravelManagement.Trial_101.finance.Repository.finance_approvalhistoryRepo;
import com.TravelManagement.Trial_101.finance.Service.financeService;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalActionResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.ApprovalHistory;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import com.TravelManagement.Trial_101.finance.Repository.finance_approvalhistoryRepo;
import com.TravelManagement.Trial_101.travelRequests.Repository.travelRepository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.TravelManagement.Trial_101.employee.employeeRepository.employeeRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class finanaceServiceImpl implements financeService {

    @Autowired
    financeRepo financeRepo;

    @Autowired
    employeeRepository employeeRepo;

    @Autowired
    TravelRepository travelRepo;

    @Autowired
    finance_approvalhistoryRepo finance_approvalRepo;


    @Override
    public FinanceApprovalActionResponseDTO finance_processApproval(FinanceApprovalActionRequestDTO dto){
        // ================================================
        // STEP 1: Validate the action (Must be APPROVED or REJECTED)
        // ================================================
        if (dto.getAction() == null ||
                (!dto.getAction().equalsIgnoreCase("APPROVED") &&
                        !dto.getAction().equalsIgnoreCase("REJECTED"))) {
            throw new IllegalArgumentException(
                    "Invalid action! Action must be either 'APPROVED' or 'REJECTED'."
            );
        }

        // ================================================
        // STEP 2: Fetch the Travel Request
        // ================================================
        TravelRequest travelRequest = travelRepo.findById(dto.getTravelReqID())
                .orElseThrow(() -> new RuntimeException(
                        "Travel Request not found for ID: " + dto.getTravelReqID()
                ));

        // ================================================
        // STEP 3: Validate Current Status
        // (Manager can only act on SUBMITTED requests)
        // ================================================
        if (travelRequest.getStatus() != TravelRequest.Status.MANAGER_APPROVED) {
            throw new IllegalStateException(
                    "Cannot process this request! " +
                            "Current status is: " + travelRequest.getStatus().name() +
                            ". Only MANAGER_SUBMITTED requests can be approved or rejected."
            );
        }

        // ================================================
        // STEP 4: Fetch the Approver (Manager)
        // ================================================
        Employee approver = employeeRepo.findById(dto.getApproverID())
                .orElseThrow(() -> new RuntimeException(
                        "Approver not found for ID: " + dto.getApproverID()
                ));

//        if (approver.getDepartment() == null ||
//                !(approver.getDepartment().getDepartmentName().equalsIgnoreCase("sales") ||
//                        approver.getDepartment().getDepartmentName().equalsIgnoreCase("sales"))) {
//            throw new IllegalArgumentException(
//                    "Only Finance or Admin department members can approve/reject requests."
//            );
//        }

//        Employee current_employee = employeeRepo.findById(dto.getEmployeeid()).get();

        Employee current_employee = employeeRepo.findById(dto.getEmployeeid())
                .orElseThrow(() -> new RuntimeException(
                        "employeeid not found" + dto.getEmployeeid()
                ));

        if (approver.getDepartment() == null ||
                !(approver.getDepartment().getDepartmentName() == current_employee.getDepartment().getDepartmentName())){
            throw new IllegalArgumentException(
                    "Only Finance of a same department  can approve/reject requests."
            );
        }

        // ================================================
        // STEP 5: Update Travel Request Status
        // ================================================
        String action = dto.getAction().toUpperCase();

        if (action.equals("APPROVED")) {
            travelRequest.setStatus(TravelRequest.Status.FINANCE_APPROVED);
        } else {
            travelRequest.setStatus(TravelRequest.Status.FINANCE_REJECTED);
        }

        TravelRequest updatedRequest = travelRepo.save(travelRequest);

        // ================================================
        // STEP 6: Save to Approval History
        // ================================================
        ApprovalHistory approvalHistory = new ApprovalHistory();
        approvalHistory.setTravelRequest(updatedRequest);
        approvalHistory.setApprover(approver);
        approvalHistory.setApprovalLevel(ApprovalHistory.ApprovalLevel.FINANCE);
        approvalHistory.setAction(ApprovalHistory.Action.valueOf(action));
        finance_approvalRepo.save(approvalHistory);

        // ================================================
        // STEP 7: Build and Return Response
        // ================================================
        return FinanceApprovalActionResponseDTO.builder()
                .message("Request " + updatedRequest.getRequestCode() +
                        " has been " + action + " successfully!")
                .requestCode(updatedRequest.getRequestCode())
                .action(action)
                .approverName(approver.getFullName())
                .newStatus(updatedRequest.getStatus().name())
                .actionDate(LocalDateTime.now())
                .build();
    }



    @Override
    public List<FinancePendingCardDTO> getManagerApprovedPendingFinance() {

        // 1. Fetch all MANAGER_APPROVED requests from DB
        List<TravelRequest> requests = financeRepo.findAllManagerApprovedFinancePending();

        // 2. Convert to DTOs
        List<FinancePendingCardDTO> dtoList = new ArrayList<>();
        for (TravelRequest tr : requests) {
            dtoList.add(mapToFinancePendingCardDTO(tr));
        }

        return dtoList;
    }


    private FinancePendingCardDTO mapToFinancePendingCardDTO(TravelRequest tr) {
        if (tr == null) return null;

        FinancePendingCardDTO dto = new FinancePendingCardDTO();

        // =====================================================
        // COLUMN 1: REQ ID (#TR-1042)
        // =====================================================
        dto.setTravelReqID(tr.getTravelReqID());
        dto.setRequestCode(tr.getRequestCode());
        // =====================================================
        // COLUMN 2: EMPLOYEE
        // (Name, Initials Avatar, Designation)
        // =====================================================
        if (tr.getEmployee() != null) {
            dto.setEmployeeName(tr.getEmployee().getFullName());
            dto.setEmployeeid(tr.getEmployee().getEmployeeID());

            // Auto-generate initials ("Jonathan Sterling" -> "JS")
//            dto.setEmployeeInitials(generateInitials(tr.getEmployee().getFullName()));



            if (tr.getEmployee().getDepartment() != null) {
                dto.setDepartmentName(
                        tr.getEmployee().getDepartment().getDepartmentName()
                );
            }
        }

        // =====================================================
        // COLUMN 3: BUDGET AMOUNT ($4,250.00 USD)
        // =====================================================
        if (tr.getRequestBudget() != null) {
            dto.setBudgetAmount(tr.getRequestBudget().getTotalBudget());
            dto.setCurrency("Rs");
        } else {
            // Fallback if no budget attached
            dto.setBudgetAmount(BigDecimal.ZERO);
            dto.setCurrency("Rs");
        }

        // =====================================================
        // COLUMN 4: POLICY COMPLIANCE
        // "Within Policy" (green) or "Policy Violation" (red)
        // =====================================================
        Boolean violation = tr.getPolicyViolation() != null && tr.getPolicyViolation();
        dto.setPolicyViolation(violation);
        dto.setPolicyStatus(violation ? "Policy Violation" : "Within Policy");

        // =====================================================
        // COLUMN 5: MANAGER APPROVAL
        // Always "Manager Approved" because status = MANAGER_APPROVED
        // =====================================================
        dto.setManagerApprovalStatus("Manager Approved");

        // =====================================================
        // CURRENT STATUS
        // =====================================================
        if (tr.getStatus() != null) {
            dto.setStatus(tr.getStatus().name());
        }

        return dto;
    }

    // =====================================================
// HELPER: Generate Initials from Full Name
// "Jonathan Sterling" -> "JS"
// "Alice"             -> "A"
// =====================================================
//    private String generateInitials(String fullName) {
//        if (fullName == null || fullName.trim().isEmpty()) return "?";
//
//        String[] parts = fullName.trim().split(" ");
//
//        if (parts.length == 1) {
//            // Only one name -> take first letter
//            return parts[0].substring(0, 1).toUpperCase();
//        }
//
//        // Take first letter of first name + first letter of last name
//        return (parts[0].substring(0, 1) +
//                parts[parts.length - 1].substring(0, 1)).toUpperCase();
//    }
//


}
