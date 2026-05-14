package com.TravelManagement.Trial_101.employee.DTO.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesignationResponseDTO {

    private Integer designationID;
    private String  designationName;
    private String  employeeLevel;
    private String  status;
}