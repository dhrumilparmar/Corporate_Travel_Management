package com.TravelManagement.Trial_101.finance.Repository;

import com.TravelManagement.Trial_101.finance.DTO.AllApprovedReqFin;
import com.TravelManagement.Trial_101.finance.DTO.FinanceRemApproval;
import com.TravelManagement.Trial_101.travelRequests.Entity.ApprovalHistory;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface financeRepo extends JpaRepository<TravelRequest, Integer> {
    @Query("""
        SELECT tr FROM TravelRequest tr
        JOIN FETCH tr.employee emp
        JOIN FETCH emp.department dept
        LEFT JOIN FETCH tr.requestBudget rb
        WHERE tr.status = 'MANAGER_APPROVED'
        ORDER BY tr.updatedAt DESC
    """)
    List<TravelRequest> findAllManagerApprovedFinancePending();

    @Query("""
    SELECT tr.employee.employeeID as employeeId, 
           tr.status as status, 
           tr.travelReqID as travelReqID, 
           tr.destination as destination, 
           tr.requestCode as requestCode, 
           rb.totalBudget as totalBudget
    FROM TravelRequest tr
    JOIN tr.requestBudget rb
    WHERE tr.status = "REM_PENDING"
    AND tr.employee.employeeID = :employeeid
""")
    List<FinanceRemApproval> getAllreqByStatus(@Param("employeeid") Integer employeeid);
}
