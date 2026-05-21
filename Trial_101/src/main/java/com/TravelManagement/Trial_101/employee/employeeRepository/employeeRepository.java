package com.TravelManagement.Trial_101.employee.employeeRepository;

import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.EmployeeResponseDTO;
import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.ManagerResponseDTO;
import com.TravelManagement.Trial_101.employee.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface employeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.employeeID IN (SELECT DISTINCT emp.manager.employeeID FROM Employee emp WHERE emp.manager IS NOT NULL)")
    List<Employee> getAllManager();

}
