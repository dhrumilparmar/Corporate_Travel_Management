package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyTransportResponseDTO {

    private Integer id;
    private Integer policyID;
    private String  policyName;
    private Integer transportID;
    private String  transportName;
}