package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;

// ─── REQUEST DTO (Create Travel Request) ────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelRequestDTO {

    private Integer   employeeID;
    private Integer   TravelReqID;
    private String    destination;
    private LocalDate startTravel;
    private LocalDate endTravel;
    private String    purpose;
    private String    justification;
    private Integer   transportID;
    private String    status;           // defaults to DRAFT

    // Budget (created together with request)
    private RequestBudgetDTO budget;
}
