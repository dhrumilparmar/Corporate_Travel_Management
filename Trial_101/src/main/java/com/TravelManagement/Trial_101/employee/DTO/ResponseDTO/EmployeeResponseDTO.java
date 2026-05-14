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

    private Integer   employeeID;
    private String    fullName;
    private String    email;
    private String    status;
    private LocalDateTime createdAt;
//    private Integer roleID;
//    private String  roleName;
//    private Integer designationID;
//    private String  designationName;
//    private String  employeeLevel;
////    private String  status;
//
//    private Integer departmentID;
//    private String  departmentName;
////    private String  status;

    // Nested objects instead of raw IDs
    private DepartmentResponseDTO  department;
//    private DesignationResponseDTO designation;
    private RoleResponseDTO        role;

    // Manager info (simple, not full nested to avoid infinite loop)
//    private EmployeeSummaryDTO manager;
}
