package com.TravelManagement.Trial_101.employee.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "employee",
        indexes = {
                @Index(name = "idx_employee_email",       columnList = "email"),
                @Index(name = "idx_employee_role",        columnList = "roleID"),
                @Index(name = "idx_employee_department",  columnList = "departmentID"),
                @Index(name = "idx_employee_manager",     columnList = "managerID"),
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeID")
    private Integer employeeID;

    @Column(name = "fullName", nullable = false, length = 150)
    private String fullName;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "passwordHash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            columnDefinition = "ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    // Many employees belong to one department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentID", referencedColumnName = "departmentID")
    private Department department;


    // Many employees have one role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID", referencedColumnName = "roleID")
    private Role role;

    // Self-referencing: Many employees report to one manager
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "managerID", referencedColumnName = "employeeID")
    private Employee manager;

    // One manager has many subordinates
//    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
//    private List<Employee> subordinates;

    // -------------------------------------------------------
    // One employee has many travel requests
    // -------------------------------------------------------
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
//    private List<TravelRequest> travelRequests;
//
//    // -------------------------------------------------------
//    // One employee has many expenses
//    // -------------------------------------------------------
//    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
//    private List<Expense> expenses;
//
//    // -------------------------------------------------------
//    // One employee (approver) has many approval histories
//    // -------------------------------------------------------
//    @OneToMany(mappedBy = "approver", fetch = FetchType.LAZY)
//    private List<ApprovalHistory> approvalHistories;

    public enum Status {
        ACTIVE, INACTIVE
    }
}