package com.TravelManagement.Trial_101.employee.employeeService;

import com.TravelManagement.Trial_101.employee.DTO.RequestDTO.EmployeeRequestDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.EmployeeResponseDTO;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface employeeService {

    EmployeeResponseDTO saveEmployee (EmployeeRequestDTO requestDTO);

    List<EmployeeResponseDTO> getAllEmployee();

    EmployeeResponseDTO getEmployee(Integer id);

    void deleteEmployeeById(Integer employeeID);

    EmployeeResponseDTO updateEmployee(Employee employee);

}
