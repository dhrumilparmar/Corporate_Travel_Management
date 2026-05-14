package com.TravelManagement.Trial_101.employee.employeeRepository;

import com.TravelManagement.Trial_101.employee.Entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepo extends JpaRepository<Designation , Integer> {
}
