package com.TravelManagement.Trial_101.employee.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "designation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designationID")
    private Integer designationID;

    @Column(name = "designationName", unique = true, nullable = false, length = 100)
    private String designationName;

    @Enumerated(EnumType.STRING)
    @Column(name = "employeeLevel", nullable = false,
            columnDefinition = "ENUM('JUNIOR','SENIOR','MANAGER','ADMIN')")
    private EmployeeLevel employeeLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            columnDefinition = "ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE'")
    private Status status = Status.ACTIVE;

    // One designation has many employees
    @OneToMany(mappedBy = "designation", fetch = FetchType.LAZY)
    private List<Employee> employees;

    // One designation has many travel policies
//    @OneToMany(mappedBy = "designation", fetch = FetchType.LAZY)
//    private List<TravelPolicy> travelPolicies;

    public enum EmployeeLevel {
        JUNIOR, SENIOR, MANAGER, ADMIN
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}
