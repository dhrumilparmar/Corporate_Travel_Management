package com.TravelManagement.Trial_101.employee.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "departmentID")
        private Integer departmentID;

        @Column(name = "departmentName", unique = true, nullable = false, length = 100)
        private String departmentName;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", columnDefinition = "ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE'")
        private Status status = Status.ACTIVE;

        // One department has many employees
        @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
        private List<Employee> employees;

        public enum Status {
            ACTIVE, INACTIVE
        }
}
