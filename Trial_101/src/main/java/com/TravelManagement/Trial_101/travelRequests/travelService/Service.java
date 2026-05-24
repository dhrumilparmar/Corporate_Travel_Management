package com.TravelManagement.Trial_101.travelRequests.travelService;

import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.TravelRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.ExpenseResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;

import java.util.List;


@org.springframework.stereotype.Service
public interface Service {
    TravelRequestResponseDTO createRequest(TravelRequestDTO travelreqDto);

    List<TravelRequestResponseDTO> getAllRequestsByEmployeeId(Integer employeeId);

    ExpenseResponseDTO submitBills(ExpenseRequestDTO expenseRequestDTO);

    void deleteRequest(Integer reqID);

    TravelRequestResponseDTO updateRequest(TravelRequestDTO dto);
}