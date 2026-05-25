package com.TravelManagement.Trial_101.finance.Controller;

import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.EmployeeResponseDTO;
import com.TravelManagement.Trial_101.employee.employeeService.employeeService;
import com.TravelManagement.Trial_101.finance.DTO.FinanceApprovalActionResponseDTO;
import com.TravelManagement.Trial_101.finance.DTO.FinancePendingCardDTO;
import com.TravelManagement.Trial_101.finance.DTO.RequestDTO.FinanceApprovalActionRequestDTO;
import com.TravelManagement.Trial_101.finance.Service.financeService;
import com.TravelManagement.Trial_101.manager.DTO.RequestDTO.ApprovalActionRequestDTO;
import com.TravelManagement.Trial_101.manager.DTO.ResponseDTO.ApprovalActionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/rest/finance")
@CrossOrigin(origins = "http://localhost:4200")
public class finanaceController {

    @Autowired
    financeService financeService;

    @Autowired
    employeeService empSvc;


    @GetMapping("/manager-approved-pending")
    public ResponseEntity<List<FinancePendingCardDTO>> getManagerApprovedPending() {
        try {
            List<FinancePendingCardDTO> requests =
                    financeService.getManagerApprovedPendingFinance();

            if (requests.isEmpty()) {
                System.out.println("list is empty");
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(requests);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/process-approval")
    public ResponseEntity<FinanceApprovalActionResponseDTO> finance_processApproval(@RequestBody FinanceApprovalActionRequestDTO dto) {
        try {
            FinanceApprovalActionResponseDTO response = financeService.finance_processApproval(dto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Bad action value (not APPROVED or REJECTED)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IllegalStateException e) {
            // Request is not in SUBMITTED status
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/profile/{employeeID}")
    public ResponseEntity<EmployeeResponseDTO> getProfile(@PathVariable Integer employeeID) {
        EmployeeResponseDTO profile = empSvc.getEmployee(employeeID);
        return ResponseEntity.ok(profile);
    }
}
