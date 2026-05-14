package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelRequestResponseDTO {

    private Integer   travelReqID;
    private String    requestCode;
    private String    destination;
    private LocalDate startTravel;
    private LocalDate endTravel;
    private Integer   tripDays;        // computed: endTravel - startTravel
    private String    purpose;
    private String    justification;
    private String    status;
    private Boolean   policyViolation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Nested objects
    private TransportModeResponseDTO transportMode;
    private RequestBudgetResponseDTO budget;
}
