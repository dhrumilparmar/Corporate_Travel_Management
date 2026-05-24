package com.TravelManagement.Trial_101.travelRequests.Repository.travelRepository;

import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<TravelRequest, Integer> {
    @Query(value = "SELECT request_code FROM travel_request WHERE request_code LIKE :prefix% ORDER BY travel_reqID DESC LIMIT 1", nativeQuery = true)
    String findLastRequestCode(@Param("prefix") String prefix);

    @Query(value = "select * from travel_request", nativeQuery = true) //jpql
    List<TravelRequestResponseDTO> getAllRequest();

    @Query("SELECT tr FROM TravelRequest tr WHERE tr.employee.employeeID = :employeeId")
    List<TravelRequest> findByEmployee_EmployeeID(@Param("employeeId") Integer employeeID);

}

