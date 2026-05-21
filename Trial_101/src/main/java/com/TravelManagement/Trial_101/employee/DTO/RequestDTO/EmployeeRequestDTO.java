package com.TravelManagement.Trial_101.employee.DTO.RequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

// ─── REQUEST DTO (Register / Create Employee) ───────────────────────
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {

    private String  fullName;
    private Integer employeeID;
    private String  email;
    private String  password;         // raw password (will be hashed)
    private Integer departmentID;     //dropdown
    private Integer roleID;           //dropdown
    private Integer managerID;        // nullable
    private String  status;
}
