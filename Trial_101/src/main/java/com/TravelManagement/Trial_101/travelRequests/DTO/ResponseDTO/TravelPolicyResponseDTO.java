package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;
//import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.DesignationResponseDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPolicyResponseDTO {

    private Integer    policyID;
    private String     policyName;
    private BigDecimal maxBudget;
    private String     status;
    private LocalDateTime createdAt;

    // Nested
//    private DesignationResponseDTO designation;
    private List<TransportModeResponseDTO> allowedTransports;
}
