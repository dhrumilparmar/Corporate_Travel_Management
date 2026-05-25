package com.TravelManagement.Trial_101.finance.Repository;

import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
