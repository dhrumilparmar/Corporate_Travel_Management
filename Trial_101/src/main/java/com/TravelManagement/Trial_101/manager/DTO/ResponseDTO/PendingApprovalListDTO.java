package com.TravelManagement.Trial_101.manager.DTO.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingApprovalListDTO {

    private Long totalPendingCount; // Shows the counter text at top of page
    private List<PendingRequestCardDTO> pendingRequests;
}
