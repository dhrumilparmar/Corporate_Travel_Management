package com.TravelManagement.Trial_101.manager.controller;

import com.TravelManagement.Trial_101.manager.DTO.RequestDTO.ApprovalActionRequestDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalActionResponseDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalHistoryResponseDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalRequestDetailDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.PendingRequestCardDTO;
import com.TravelManagement.Trial_101.manager.managerService.managerSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/manager")
@CrossOrigin(origins = "http://localhost:4200")
public class managerController {
    @Autowired
    private managerSvc managerSvc;

        @GetMapping("/pending-requests/{managerID}")
    public ResponseEntity<List<PendingRequestCardDTO>> getPendingRequestsForReview(@PathVariable(name = "managerID") Integer managerID){
        try{
            return ResponseEntity.ok(managerSvc.getPendingReqReview(managerID));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/request-detail/{travelReqID}")
    public ResponseEntity<ApprovalRequestDetailDTO> getDetailedRequest(@PathVariable Integer travelReqID) {
        try {
            ApprovalRequestDetailDTO detail = managerSvc.getDetailedRequest(travelReqID);
            return ResponseEntity.ok(detail);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    // 👇 NEW: Approve / Reject endpoint
    @PostMapping("/process-approval")
    public ResponseEntity<ApprovalActionResponseDTO> processApproval(@RequestBody ApprovalActionRequestDTO dto) {
        try {
            ApprovalActionResponseDTO response = managerSvc.processApproval(dto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Bad action value (not APPROVED or REJECTED)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IllegalStateException e) {
            // Request is not in SUBMITTED status
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/approved-by/{approverID}")
    public ResponseEntity<List<ApprovalHistoryResponseDTO>> getApprovedRequests(
            @PathVariable Integer approverID) {
        try {
            List<ApprovalHistoryResponseDTO> approvedList =
                    managerSvc.getAllApprovedRequests(approverID);

            if (approvedList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(approvedList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }




    }

    @GetMapping("/{managerID}/pending-requests")
    public ResponseEntity<List<PendingRequestCardDTO>> findTravelRequestsByManager(@PathVariable("managerID") Integer managerID) {
        try {
            List<PendingRequestCardDTO> pendingRequests =
                    managerSvc.findTravelRequestsByManagerId(managerID);
            return ResponseEntity.ok(pendingRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/allrequest/{id}")
    public ResponseEntity<List<ApprovalHistoryResponseDTO>> getAllRequestHistroy(@PathVariable("id") Integer managerid){
            try{
                List<ApprovalHistoryResponseDTO> allRequest =
                        managerSvc.getAllApprovedRequests(managerid);
                return ResponseEntity.ok(allRequest);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

            }
    }

}
