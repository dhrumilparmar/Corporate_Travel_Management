package com.TravelManagement.Trial_101.employee.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class ManagerResponseDTO {
    private Integer employeeID;
    private String fullName;


}
