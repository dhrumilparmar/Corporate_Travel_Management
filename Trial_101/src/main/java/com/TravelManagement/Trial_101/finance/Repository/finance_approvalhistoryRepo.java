package com.TravelManagement.Trial_101.finance.Repository;

import com.TravelManagement.Trial_101.travelRequests.Entity.ApprovalHistory;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface finance_approvalhistoryRepo extends JpaRepository<ApprovalHistory, Integer> {
}
