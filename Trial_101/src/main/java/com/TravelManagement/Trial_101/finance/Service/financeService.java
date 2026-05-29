package com.TravelManagement.Trial_101.finance.Service;

import com.TravelManagement.Trial_101.finance.DTO.AllApprovedReqFin;
import com.TravelManagement.Trial_101.finance.DTO.FinanceApprovalActionResponseDTO;
import com.TravelManagement.Trial_101.finance.DTO.FinancePendingCardDTO;
import com.TravelManagement.Trial_101.finance.DTO.FinanceRemApproval;
import com.TravelManagement.Trial_101.finance.DTO.RequestDTO.FinanceApprovalActionRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface financeService {
    List<FinancePendingCardDTO> getManagerApprovedPendingFinance();

    FinanceApprovalActionResponseDTO finance_processApproval(FinanceApprovalActionRequestDTO dto);

    List<AllApprovedReqFin> allApprovedReq(Integer financeid);

    List<FinanceRemApproval> getAllremReqs(Integer employeeid);
}
