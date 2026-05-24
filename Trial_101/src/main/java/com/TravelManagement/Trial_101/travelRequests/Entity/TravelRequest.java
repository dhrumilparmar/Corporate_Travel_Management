package com.TravelManagement.Trial_101.travelRequests.Entity;


import com.TravelManagement.Trial_101.employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "travel_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travelReqID")
    private Integer travelReqID;

    @Column(name = "requestCode", unique = true, nullable = false, length = 20)
    private String requestCode;

    @Column(name = "destination", nullable = false, length = 150)
    private String destination;

    @Column(name = "startTravel", nullable = false)
    private LocalDate startTravel;

    @Column(name = "endTravel", nullable = false)
    private LocalDate endTravel;

    @Column(name = "purpose", nullable = false, length = 150)
    private String purpose;

    @Column(name = "justification", columnDefinition = "TEXT")
    private String justification;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            columnDefinition = "ENUM('DRAFT','SUBMITTED','MANAGER_APPROVED'," +
                    "'FINANCE_APPROVED','MANAGER_REJECTED'," +
                    "'FINANCE_REJECTED','CANCELLED') DEFAULT 'DRAFT'")
    private Status status = Status.DRAFT;

    @Column(name = "policyViolation", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean policyViolation = false;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // Many travel requests belong to one employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID", referencedColumnName = "employeeID",
            nullable = false)
    private Employee employee;

    // Many travel requests use one transport mode
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportID", referencedColumnName = "transportID",
            nullable = false)
    private TransportMode transportMode;

    // One travel request has exactly ONE budget (OneToOne)
    @OneToOne(mappedBy = "travelRequest",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private RequestBudget requestBudget;

    // One travel request has many expenses
    @OneToMany(mappedBy = "travelRequest",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Expense> expenses;

    // One travel requeTst has many approval histories
    @OneToMany(mappedBy = "travelRequest",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ApprovalHistory> approvalHistories;

    public enum Status {
        DRAFT, SUBMITTED, MANAGER_APPROVED,
        FINANCE_APPROVED, MANAGER_REJECTED,
        FINANCE_REJECTED, CANCELLED
    }
}