package com.TravelManagement.Trial_101.employee.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {

    private Integer employeeID;
    private String fullName;
    private String email;
    private String status;
    private LocalDateTime createdAt;
    private String passwordHash;

    // These should be simple types
    private Integer managerID;      // Just the ID (Integer)
    private String managerName;     // Just the name (String) - fetched from employee table

    private DepartmentResponseDTO department;
    private RoleResponseDTO role;
}