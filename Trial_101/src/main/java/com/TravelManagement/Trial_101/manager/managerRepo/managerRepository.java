package com.TravelManagement.Trial_101.manager.managerRepo;

import com.TravelManagement.Trial_101.employee.Entity.Employee;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalHistoryResponseDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.PendingRequestCardDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface managerRepository extends JpaRepository<TravelRequest, Integer> {

    // For Department Managers: get only pending requests from their own department

//    @Query("SELECT tr FROM TravelRequest tr " +
//            "LEFT JOIN FETCH tr.requestBudget " +  // 👈 Add this line to fetch budget
//            "INNER JOIN Employee e ON tr.employee.employeeID = e.employeeID " +
//            "INNER JOIN Employee m ON e.manager.employeeID = m.employeeID " +
//            "WHERE e.manager.employeeID = :managerId " +
//            "AND tr.status = com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest$Status.SUBMITTED")
//    List<TravelRequest> findAllPendingRequestsForManager(@Param("managerId") Integer managerId);
//
//
    @Query(value = "select tr.travel_reqid, tr.destination, tr.justification, tr.policy_violation, tr.purpose, tr.request_code,\n" +
            " tr.start_travel,tr.end_travel, tr.status, tr.updated_at," +
            " tr.employeeid," +
            " tr.transportid ," +
            " rb. total_budget," +
            " em.full_name," +
            " tm.transport_name," +
            "tr.created_at " +
            " from travel_request tr" +
            " join request_budget rb" +
            " on tr.travel_reqid = rb.travel_reqid" +
            " join employee em" +
            " on tr.employeeid = em.employeeid" +
            " join  transport_mode tm" +
            " on tr.transportid = tm.transportid" +
            " where em.managerid = :managerid" +
            " and tr.status = 'SUBMITTED'", nativeQuery = true)
    List<TravelRequest> findAllPendingRequestsForManager(@Param("managerid") Integer managerid);





    @Query("SELECT tr FROM TravelRequest tr JOIN FETCH tr.employee emp  JOIN FETCH emp.department dept JOIN FETCH tr.transportMode tm LEFT JOIN FETCH tr.requestBudget rb WHERE tr.travelReqID = :travelReqID")
    Optional<TravelRequest> findDetailedRequestById(
            @Param("travelReqID") Integer travelReqID
    );

    @Query(value = """
    SELECT tr.travel_reqID,tr.request_code, tr.destination, tr.start_travel, tr.end_travel, tr.purpose, tr.status, e.full_name AS employeeName, ah.approval_level, ah.action, ah.remarks, ah.action_date AS approvedDate
    FROM approval_history ah
    JOIN travel_request tr ON ah.travel_reqid = tr.travel_reqID
    JOIN employee e ON tr.employeeid = e.employeeID WHERE ah.approverid = :approverID AND ah.action = 'APPROVED' ORDER BY ah.action_date DESC
""", nativeQuery = true)
    List<ApprovalHistoryResponseDTO> getAllApprovedReq(@Param("approverID") Integer approverID);
//
//    @Query(value = "SELECT " +
//            "tr.travel_reqid, " +                    // index 0
//            "tr.request_code, " +                   // index 1 - requestCode
//            "tr.destination, " +                    // index 2 - destination
//            "tr.start_travel, " +                   // index 3 - startDate
//            "tr.end_travel, " +                     // index 4 - endDate
//            "tr.purpose, " +                        // index 5
//            "tr.justification, " +                  // index 6
//            "tr.status, " +                         // index 7 - status
//            "tr.created_at, " +                     // index 8
//            "tr.updated_at, " +                     // index 9
//            "e.full_name, " +                       // index 10 - employeeName
//            "e.email, " +                           // index 11
//            "m.full_name, " +                       // index 12
//            "m.email " +                            // index 13
//            "FROM travel_request tr " +
//            "INNER JOIN employee e ON tr.employeeid = e.employeeid " +
//            "INNER JOIN employee m ON e.managerid = m.employeeid " +
//            "WHERE e.managerid = :managerId",
//            nativeQuery = true)
//    List<PendingRequestCardDTO> findTravelRequestsByManagerIdNative(@Param("managerId") Integer managerId);
//
//
}


