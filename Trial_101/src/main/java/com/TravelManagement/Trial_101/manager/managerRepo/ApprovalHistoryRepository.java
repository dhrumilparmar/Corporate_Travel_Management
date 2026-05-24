package com.TravelManagement.Trial_101.manager.managerRepo;

import com.TravelManagement.Trial_101.travelRequests.Entity.ApprovalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalHistoryRepository extends JpaRepository<ApprovalHistory, Integer> {
}
