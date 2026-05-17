package com.TravelManagement.Trial_101.employee.employeeController;

import com.TravelManagement.Trial_101.employee.DTO.RequestDTO.EmployeeRequestDTO;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import com.TravelManagement.Trial_101.employee.employeeService.employeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.EmployeeResponseDTO;

import java.util.List;


@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:4200") // or configure globally
public class EmployeeController {
    @Autowired
    employeeService empSvc;

    @GetMapping("/profile/{employeeID}")
    public ResponseEntity<EmployeeResponseDTO> getProfile(@PathVariable Integer employeeID) {
        EmployeeResponseDTO profile = empSvc.getEmployee(employeeID);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        try {
            List<EmployeeResponseDTO> employees = (List<EmployeeResponseDTO>) empSvc.getAllEmployee();

            // Optional: Check if list is empty
            if (employees.isEmpty()) {
                return ResponseEntity.noContent().build(); // Returns 204 No Content
            }
            return ResponseEntity.ok(employees); // Returns 200 OK with the List

        } catch (Exception e) {
            e.printStackTrace(); // Always log the error for debugging!
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeResponseDTO> saveEmp(@RequestBody EmployeeRequestDTO requestDTO){
        try{
            return ResponseEntity.ok(empSvc.saveEmployee(requestDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteEmployee/{employeeID}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer employeeID){
        try{
            empSvc.deleteEmployeeById(employeeID);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/updateEmployee")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestBody Employee employee){
        try{
            return ResponseEntity.ok(empSvc.updateEmployee(employee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}