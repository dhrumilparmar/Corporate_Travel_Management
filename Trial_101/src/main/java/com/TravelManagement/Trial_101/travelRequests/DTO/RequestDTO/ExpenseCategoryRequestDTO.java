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
public class ExpenseCategoryRequestDTO {

    private String categoryName;
}
