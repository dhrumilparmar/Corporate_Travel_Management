package com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// ─── REQUEST DTO ────────────────────────────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPolicyRequestDTO {

//    private Integer    designationID;
    private BigDecimal maxBudget;
    private String     policyName;
    private String     status;

    // Transport IDs allowed for this policy
    private List<Integer> transportIDs;
}