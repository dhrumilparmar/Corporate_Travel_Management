package com.TravelManagement.Trial_101.employee.DTO.RequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// ─── REQUEST DTO ────────────────────────────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignationRequestDTO {

    private String designationName;
    private String employeeLevel;   // JUNIOR, SENIOR, MANAGER, ADMIN
    private String status;
}
