package com.TravelManagement.Trial_101.manager.managerService;

import com.TravelManagement.Trial_101.manager.DTO.RequestDTO.ApprovalActionRequestDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalActionResponseDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalHistoryResponseDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalRequestDetailDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.PendingRequestCardDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface managerSvc {
    List<PendingRequestCardDTO> getPendingReqReview(Integer managerID);

    ApprovalRequestDetailDTO getDetailedRequest(Integer travelReqID);

    ApprovalActionResponseDTO processApproval(ApprovalActionRequestDTO approvalActionRequestDTO);

    List<ApprovalHistoryResponseDTO> getAllApprovedRequests(Integer approverID);

    List<PendingRequestCardDTO> findTravelRequestsByManagerId(Integer managerID);

}
