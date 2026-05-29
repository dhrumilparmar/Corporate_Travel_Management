package com.TravelManagement.Trial_101.travelRequests.travelService;

import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseCreateRequest;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.TravelRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.ExpenseResponse;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.ExpenseResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestFinanceDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.Expense;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;


@org.springframework.stereotype.Service
public interface Service {
    TravelRequestResponseDTO createRequest(TravelRequestDTO travelreqDto);

    List<TravelRequestResponseDTO> getAllRequestsByEmployeeId(Integer employeeId);

//    ExpenseResponseDTO submitBills(ExpenseRequestDTO expenseRequestDTO);

    void deleteRequest(Integer reqID);

    TravelRequestResponseDTO updateRequest(TravelRequestDTO dto);

    List<TravelRequestFinanceDTO> getAllfinApproved(Integer employeeid);

    ExpenseResponseDTO addExpense(ExpenseRequestDTO expenseRequestDTO);

    List<ExpenseResponse> saveExpenses(ExpenseCreateRequest request);

}