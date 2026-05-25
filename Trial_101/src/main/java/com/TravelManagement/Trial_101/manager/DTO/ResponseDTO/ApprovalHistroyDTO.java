package com.TravelManagement.Trial_101.manager.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalHistroyDTO {
    private Integer travelReqID;

    private String requestCode;

    private String department;

    private BigDecimal budget;

    private String policyStatus;

    private LocalDate startTravel;

    private LocalDate endTravel;

    private String purpose;

    private String status;

    private String employeeName;

    private String approvalLevel;

    private String action;

    private String remarks;

    private LocalDateTime approvedDate;
}
