package com.TravelManagement.Trial_101.employee.employeeRepository;

import com.TravelManagement.Trial_101.employee.Entity.Role;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Rolerepo extends JpaRepository<Role, Integer> {
}
