package com.TravelManagement.Trial_101.travelRequests.Controller;

import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.TravelRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.ExpenseResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.Entity.TravelRequest;
import com.TravelManagement.Trial_101.travelRequests.travelService.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelrequest")
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

    @DeleteMapping("/deleteRequest")
    public ResponseEntity<Void> deleteTravelRequest(@RequestBody TravelRequest travelRequest){
        try{
            travelSvc.deleteRequest(travelRequest);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/allRequest")
    public ResponseEntity<List<TravelRequestResponseDTO>> allMyRequest() { // ✅ 1. Added List<>
        try {
            List<TravelRequestResponseDTO> requests = travelSvc.getAllRequest();
            // Optional: Check if list is empty
            if (requests.isEmpty()) {
                return ResponseEntity.noContent().build(); // Returns 204 No Content
            }

            // ✅ 2. Removed the invalid (TravelRequestResponseDTO) cast
            return ResponseEntity.ok(requests);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addExpense")
    public ResponseEntity<ExpenseResponseDTO> addExpense(@RequestBody ExpenseRequestDTO dto){
        try{
            return ResponseEntity.ok(travelSvc.submitBills(dto));
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/updateRequest")
    public ResponseEntity<TravelRequestResponseDTO> updtaedRequests(@RequestBody TravelRequestDTO travelRequest){
        try{
            return ResponseEntity.ok(travelSvc.updateRequest(travelRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
