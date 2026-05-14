package com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCategoryResponseDTO {

    private Integer categoryID;
    private String  categoryName;
}
