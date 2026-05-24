package com.TravelManagement.Trial_101.manager.DTO.ResponseDTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalActionResponseDTO {

    private String  message;        // "Request TR-2026-001 has been APPROVED successfully!"
    private String  requestCode;    // TR-2026-001
    private String  action;         // APPROVED / REJECTED
    private String  approverName;   // Michael Manager
    private String  remarks;        // Manager's comments
    private String  newStatus;      // MANAGER_APPROVED / MANAGER_REJECTED
    private LocalDateTime actionDate; // When it was approved
}
