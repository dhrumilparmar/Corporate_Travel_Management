package com.TravelManagement.Trial_101.finance.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllApprovedReqFin {

    private String employeeName;
    private String department;
    private Integer approverId;
    private String approverName;
    private String destination;
    private String status;
    private LocalDateTime approvedTime;
    private LocalDate end_travel;
    private LocalDate start_travel;
    private String travelRequest;

}
