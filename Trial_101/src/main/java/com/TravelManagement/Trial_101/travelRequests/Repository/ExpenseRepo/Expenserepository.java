package com.TravelManagement.Trial_101.travelRequests.Repository.ExpenseRepo;

import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestFinanceDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.Expense;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Expenserepository extends JpaRepository<Expense, Integer> {



    @Query(value = "SELECT " +
            "tr.travel_reqid, " +
            "tr.request_code,"+
            "tr.destination, " +
            "tr.start_travel, " +
            "tr.end_travel, " +
            "rb.total_budget, " +
            "tr.status, " +
            "tr.employeeid " +
            "FROM travel_request tr " +
            "LEFT JOIN request_budget rb ON tr.travel_reqid = rb.travel_reqid " +
            "WHERE tr.status = 'FINANCE_APPROVED' " +
            "AND tr.employeeid = :employeeId", // 1. Removed space, 2. Used proper parameter name
            nativeQuery = true)
    List<TravelRequestFinanceDTO> getAllfinanceApproved(@Param("employeeId") Integer employeeId); // 3. Match param name
}
