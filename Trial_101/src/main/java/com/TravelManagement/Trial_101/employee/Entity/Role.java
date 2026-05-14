package com.TravelManagement.Trial_101.employee.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleID")
    private Integer roleID;

    @Enumerated(EnumType.STRING)
    @Column(name = "roleName", unique = true, nullable = false,
            columnDefinition = "ENUM('EMPLOYEE','MANAGER','FINANCE','ADMIN')")
    private RoleName roleName;

    // One role has many employees
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Employee> employees;

    public enum RoleName {
        EMPLOYEE, MANAGER, FINANCE, ADMIN
    }
}