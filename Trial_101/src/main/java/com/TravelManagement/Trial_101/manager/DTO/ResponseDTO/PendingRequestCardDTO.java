package com.TravelManagement.Trial_101.manager.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingRequestCardDTO {

    private String requestCode;       // TR-1025
    private String employeeName;      // Sarah Jenkins
    private String destination;        // Singapore, SG
    private LocalDate startDate; // Oct 22
    private LocalDate endDate;  // Oct 25
    private String status;             // PENDING badge
    private BigDecimal TotalBudget;
    private Boolean policyViolation;
    private String justification;
    private Integer travel_reqid;
    private Integer employeeid;
}