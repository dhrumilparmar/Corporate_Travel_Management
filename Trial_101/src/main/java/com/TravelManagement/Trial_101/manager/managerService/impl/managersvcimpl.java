package com.TravelManagement.Trial_101.manager.managerService.impl;

import com.TravelManagement.Trial_101.employee.Entity.Department;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import com.TravelManagement.Trial_101.employee.employeeRepository.employeeRepository;
import com.TravelManagement.Trial_101.manager.DTO.RequestDTO.ApprovalActionRequestDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalActionResponseDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalHistoryResponseDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalRequestDetailDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.PendingRequestCardDTO;
import com.TravelManagement.Trial_101.manager.managerRepo.ApprovalHistoryRepository;
import com.TravelManagement.Trial_101.manager.managerRepo.managerRepository;
import com.TravelManagement.Trial_101.manager.managerService.managerSvc;
import com.TravelManagement.Trial_101.travelRequests.Entity.ApprovalHistory;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.TravelManagement.Trial_101.travelRequests.Repository.travelRepository.TravelRepository;

@Service
public class managersvcimpl implements managerSvc {

    @Autowired
    managerRepository managerRepo;

    @Autowired
    TravelRepository travelRepo;

    @Autowired
    employeeRepository employeeRepo;

    @Autowired
    ApprovalHistoryRepository approvalhistoryRepo;

    @Override
    public List<ApprovalHistoryResponseDTO> getAllApprovedRequests(Integer approverID) {
        return managerRepo.getAllApprovedReq(approverID);
    }


    @Override
    public ApprovalActionResponseDTO processApproval(ApprovalActionRequestDTO dto) {

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
        if (travelRequest.getStatus() != TravelRequest.Status.SUBMITTED) {
            throw new IllegalStateException(
                    "Cannot process this request! " +
                            "Current status is: " + travelRequest.getStatus().name() +
                            ". Only SUBMITTED requests can be approved or rejected."
            );
        }

        // STEP 4: Fetch the Approver (Manager)

        Employee approver = employeeRepo.findById(dto.getApproverID())
                .orElseThrow(() -> new RuntimeException(
                        "Approver not found for ID: " + dto.getApproverID()
                ));
//
        Employee current_employee = employeeRepo.findById(dto.getEmployeeid())
                .orElseThrow(() -> new RuntimeException(
                        "employeeid not found" + dto.getEmployeeid()
        ));
        if (approver.getDepartment() == null ||
                !(approver.getDepartment().getDepartmentName() == current_employee.getDepartment().getDepartmentName())){
            throw new IllegalArgumentException(
                    "Only Manager of a same department  can approve/reject requests."
            );
        }


//        String approverdept = dto.getEmployeeid().getDepartment().getDepartmentName();
//        Employee employeeDept = dto.getEmployeeid().getDepartment().getDepartmentName();
//
//        if (approverdept == null || employeeDept == null) {
//            throw new IllegalArgumentException("Department information is missing.");
//        }
//
//        if (!approverdept.equalsIgnoreCase(employeeDept)) {
//
//            throw new IllegalArgumentException(
//                    "Approver and Employee must belong to the same department."
//            );
//        }


//        if (approver.getRole() == null ||
//                !(approver.getRole().getRoleName().equals("MANAGER"))) {
//            throw new IllegalArgumentException(
//                    "Only Manager can approve/reject requests."
//            );
//        }


        // STEP 5: Update Travel Request Status
        String action = dto.getAction().toUpperCase();

        if (action.equals("APPROVED")) {
            travelRequest.setStatus(TravelRequest.Status.MANAGER_APPROVED);
        } else {
            travelRequest.setStatus(TravelRequest.Status.MANAGER_REJECTED);
        }

        TravelRequest updatedRequest = travelRepo.save(travelRequest);

        // ================================================
        // STEP 6: Save to Approval History
        // ================================================
        ApprovalHistory approvalHistory = new ApprovalHistory();
        approvalHistory.setTravelRequest(updatedRequest);
        approvalHistory.setApprover(approver);
        approvalHistory.setApprovalLevel(ApprovalHistory.ApprovalLevel.MANAGER);
        approvalHistory.setAction(ApprovalHistory.Action.valueOf(action));
        approvalHistory.setRemarks(dto.getRemarks());
        approvalhistoryRepo.save(approvalHistory);

        // ================================================
        // STEP 7: Build and Return Response
        // ================================================
        return ApprovalActionResponseDTO.builder()
                .message("Request " + updatedRequest.getRequestCode() +
                        " has been " + action + " successfully!")
                .requestCode(updatedRequest.getRequestCode())
                .action(action)
                .approverName(approver.getFullName())
                .remarks(dto.getRemarks())
                .newStatus(updatedRequest.getStatus().name())
                .actionDate(LocalDateTime.now())
                .build();
    }


    @Override
    public ApprovalRequestDetailDTO getDetailedRequest(Integer travelReqID) {

        // 1. Fetch the full TravelRequest with all joins
        TravelRequest tr = managerRepo.findDetailedRequestById(travelReqID)
                .orElseThrow(() -> new RuntimeException("Travel Request not found for ID: " + travelReqID));

        // 2. Map to DTO
        return mapToApprovalRequestDetailDTO(tr);
    }

    @Override
    public List<PendingRequestCardDTO> getPendingReqReview(Integer managerID) {
        // 1. Fetch the pending requests for the manager
        List<TravelRequest> requests = managerRepo.findAllPendingRequestsForManager(managerID);

        // 2. 🪄 STREAM MAPPING ➡️ Convert List<Entity> to List<DTO>
        return requests.stream()
                .map(this::mapToPendingRequestCardDto)
                .collect(Collectors.toList());
    }
    private PendingRequestCardDTO mapToPendingRequestCardDto(TravelRequest tr) {
        PendingRequestCardDTO dto = new PendingRequestCardDTO();

        dto.setRequestCode(tr.getRequestCode());
        dto.setEmployeeName(tr.getEmployee().getFullName());
        dto.setDestination(tr.getDestination());

        // ✅ Direct assignment (no conversion needed)
        dto.setStartDate(tr.getStartTravel());
        dto.setEndDate(tr.getEndTravel());

        dto.setStatus(tr.getStatus() != null ? tr.getStatus().name() : null);

        return dto;
    }

    private ApprovalRequestDetailDTO mapToApprovalRequestDetailDTO(TravelRequest tr) {
        ApprovalRequestDetailDTO dto = new ApprovalRequestDetailDTO();

        // Employee header card (top right of your screenshot)
        dto.setEmployeeName(tr.getEmployee().getFullName());

        // Request Details section
        dto.setRequestCode(tr.getRequestCode());
        dto.setDestination(tr.getDestination());
        dto.setStartTravel(tr.getStartTravel().format(DateTimeFormatter.ofPattern("MMM dd")));
        dto.setEndTravel(tr.getEndTravel().format(DateTimeFormatter.ofPattern("MMM dd")));
        dto.setPurpose(tr.getPurpose());

        // Budget section
        if (tr.getRequestBudget() != null) {
            dto.setTotalBudget(tr.getRequestBudget().getTotalBudget());
            dto.setTravelAmount(tr.getRequestBudget().getTravelAmount());
            dto.setAccommodationAmount(tr.getRequestBudget().getAccommodationAmount());
            dto.setMealsAmount(tr.getRequestBudget().getMealsAmount());
            dto.setLocalTransportAmount(tr.getRequestBudget().getLocalTransportAmount());
        }

        // Policy Status (green badge on your screenshot)
        if (tr.getPolicyViolation() != null && tr.getPolicyViolation()) {
            dto.setPolicyStatus("Policy Violation Detected");
            dto.setPolicyCompliant(false);
        } else {
            dto.setPolicyStatus("Within Corporate Guidelines");
            dto.setPolicyCompliant(true);
        }

        return dto;
    }


    @Override
    public List<PendingRequestCardDTO> findTravelRequestsByManagerId(Integer managerID){
        List<TravelRequest> results = managerRepo.findAllPendingRequestsForManager(managerID);

        return results.stream()
                .map(this::mapTomangerResponseDTO)  // ✅ Specific mapping method
                .collect(Collectors.toList());
    }
    private PendingRequestCardDTO mapTomangerResponseDTO(TravelRequest travelRequest) {
        return PendingRequestCardDTO.builder()
                .destination(travelRequest.getDestination())
                .employeeName(travelRequest.getEmployee().getFullName())
                .status(String.valueOf(travelRequest.getStatus()))
                .endDate(travelRequest.getEndTravel())
                .startDate(travelRequest.getStartTravel())
                .requestCode(travelRequest.getRequestCode())
                .policyViolation(travelRequest.getPolicyViolation())
                .justification(travelRequest.getJustification())
                .TotalBudget(travelRequest.getRequestBudget().getTotalBudget())
                .travel_reqid(travelRequest.getTravelReqID())
                .employeeid(travelRequest.getEmployee().getEmployeeID())
                .build();
    }

}


