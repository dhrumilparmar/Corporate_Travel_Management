package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelRequestStatusDTO {

    private String status;    // e.g. SUBMITTED, CANCELLED
}
