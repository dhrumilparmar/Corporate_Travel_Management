package com.TravelManagement.Trial_101.travelRequests.Repository.travelRepository;

import com.TravelManagement.Trial_101.travelRequests.Entity.RequestBudget;
import com.TravelManagement.Trial_101.travelRequests.Entity.TransportMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportModeRepository extends JpaRepository<TransportMode, Integer> {
}
