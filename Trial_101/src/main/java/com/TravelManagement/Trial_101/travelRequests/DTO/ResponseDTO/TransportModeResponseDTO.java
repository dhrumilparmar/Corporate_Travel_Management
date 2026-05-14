package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportModeResponseDTO {

    private Integer transportID;
    private String  transportName;
    private String  status;
}
