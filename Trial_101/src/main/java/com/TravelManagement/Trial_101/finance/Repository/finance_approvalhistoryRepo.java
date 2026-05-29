package com.TravelManagement.Trial_101.finance.Repository;

import com.TravelManagement.Trial_101.finance.DTO.AllApprovedReqFin;
import com.TravelManagement.Trial_101.travelRequests.Entity.ApprovalHistory;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface finance_approvalhistoryRepo extends JpaRepository<ApprovalHistory, Integer> {
    @Query(value = """
    SELECT
        emp.full_name AS employeeName,
        dept.department_name AS department,
        ah.approverid AS approverId,
        approver.full_name AS approverName,
        tr.destination AS destination,
        tr.status AS status,
        ah.action_date AS approvedTime,
        tr.end_travel AS end_travel,
        tr.start_travel AS start_travel,
        tr.request_code as travelRequest
    FROM approval_history ah
    JOIN travel_request tr
        ON ah.travel_reqid = tr.travel_reqid
    JOIN employee emp
        ON tr.employeeid = emp.employeeid
    JOIN employee approver
        ON ah.approverid = approver.employeeid
    LEFT JOIN department dept
        ON emp.departmentid = dept.departmentid
    WHERE ah.approverid = :approverId
    """, nativeQuery = true)
    List<AllApprovedReqFin> getAllApprovedReq(@Param("approverId") Integer approverId);
}
