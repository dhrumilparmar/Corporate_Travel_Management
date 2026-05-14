package com.TravelManagement.Trial_101.employee.DTO.RequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequestDTO {

    private String roleName;   // EMPLOYEE, MANAGER, FINANCE, ADMIN
}

