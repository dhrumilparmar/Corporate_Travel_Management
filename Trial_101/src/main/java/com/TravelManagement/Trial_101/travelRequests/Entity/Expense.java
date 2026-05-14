package com.TravelManagement.Trial_101.travelRequests.Entity;


import com.TravelManagement.Trial_101.employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense",
        indexes = {
                @Index(name = "idx_expense_request",  columnList = "travelReqID"),
                @Index(name = "idx_expense_employee", columnList = "employeeID"),
                @Index(name = "idx_expense_status",   columnList = "status")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expenseID")
    private Integer expenseID;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "expenseDate", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "receiptFile", length = 255)
    private String receiptFile;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            columnDefinition = "ENUM('PROCESSING','APPROVED','REJECTED') DEFAULT 'PROCESSING'")
    private Status status = Status.PROCESSING;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    // -------------------------------------------------------
    // Many expenses belong to one travel request
    // -------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelReqID", referencedColumnName = "travelReqID",
            nullable = false)
    private TravelRequest travelRequest;

    // -------------------------------------------------------
    // Many expenses belong to one employee
    // -------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID", referencedColumnName = "employeeID",
            nullable = false)
    private Employee employee;

    // -------------------------------------------------------
    // Many expenses belong to one category
    // -------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryID", referencedColumnName = "categoryID",
            nullable = false)
    private ExpenseCategory expenseCategory;

    public enum Status {
        PROCESSING, APPROVED, REJECTED
    }
}