package com.TravelManagement.Trial_101.employee.employeeRepository;

import com.TravelManagement.Trial_101.employee.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface employeeRepository extends JpaRepository<Employee, Integer> {

}
