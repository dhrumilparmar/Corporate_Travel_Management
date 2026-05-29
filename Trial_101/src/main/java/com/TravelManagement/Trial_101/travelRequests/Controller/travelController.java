package com.TravelManagement.Trial_101.travelRequests.Controller;

import com.TravelManagement.Trial_101.finance.DTO.AllApprovedReqFin;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseCreateRequest;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.TravelRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.ExpenseResponse;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.ExpenseResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestFinanceDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import com.TravelManagement.Trial_101.travelRequests.travelService.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelrequest")
@CrossOrigin(origins = "http://localhost:4200")
public class travelController {

    @Autowired
    Service travelSvc;

    @PostMapping("/createRequest")
    public ResponseEntity<TravelRequestResponseDTO> createRequest(@RequestBody TravelRequestDTO dto){
        try{
            return ResponseEntity.ok(travelSvc.createRequest(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteRequest/{ReqID}")
    public ResponseEntity<Void> deleteTravelRequest(@PathVariable("ReqID") Integer ReqID){
        try{
            travelSvc.deleteRequest(ReqID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/requests/{employeeId}")
    public ResponseEntity<List<TravelRequestResponseDTO>> getRequestsByEmployee(@PathVariable("employeeId") Integer employeeId) {
        try {
            List<TravelRequestResponseDTO> requests = travelSvc.getAllRequestsByEmployeeId(employeeId);

            if (requests.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(requests);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addExpense")
    public ResponseEntity<ExpenseResponseDTO> addExpense(@RequestBody ExpenseRequestDTO dto){
        try{
            return ResponseEntity.ok(travelSvc.addExpense(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/updateRequest")
    public ResponseEntity<TravelRequestResponseDTO> updateRequest(@RequestBody TravelRequestDTO travelRequest){
        try{
            return ResponseEntity.ok(travelSvc.updateRequest(travelRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/expenseList/{id}")
    public ResponseEntity<List<TravelRequestFinanceDTO>> getAlltravelReqExpense(@PathVariable("id") Integer id){
        try{
            List<TravelRequestFinanceDTO> requests = travelSvc.getAllfinApproved(id);
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/expenses")
    public ResponseEntity<List<ExpenseResponse>> addExpenses(@RequestBody ExpenseCreateRequest request) {
        try {
            List<ExpenseResponse> response = travelSvc.saveExpenses(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
