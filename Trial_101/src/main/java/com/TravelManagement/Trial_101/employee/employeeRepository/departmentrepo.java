package com.TravelManagement.Trial_101.employee.employeeRepository;

import com.TravelManagement.Trial_101.employee.DTO.ResponseDTO.DepartmentResponseDTO;
import com.TravelManagement.Trial_101.employee.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface departmentrepo extends JpaRepository<Department, Integer> {
}
