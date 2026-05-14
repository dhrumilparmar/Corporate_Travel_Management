package com.TravelManagement.Trial_101.employee.DTO.RequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// ─── REQUEST DTO (Client sends this) ───────────────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRequestDTO {

    private String departmentName;
    private String status;          // "ACTIVE" or "INACTIVE"
}
