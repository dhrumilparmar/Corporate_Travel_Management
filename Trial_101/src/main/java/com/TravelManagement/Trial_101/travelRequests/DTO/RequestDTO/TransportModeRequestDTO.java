package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// ─── REQUEST DTO ────────────────────────────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportModeRequestDTO {

    private String transportName;   // ROAD, TRAIN, FLIGHT
    private String status;
}
