package com.TravelManagement.Trial_101.travelRequests.Entity;


import com.TravelManagement.Trial_101.employee.Entity.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "approval_history",
        indexes = {
                @Index(name = "idx_approval_request",  columnList = "travelReqID"),
                @Index(name = "idx_approval_approver", columnList = "approverID")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approvalID")
    private Integer approvalID;

    @Enumerated(EnumType.STRING)
    @Column(name = "approvalLevel", nullable = false,
            columnDefinition = "ENUM('MANAGER','FINANCE')")
    private ApprovalLevel approvalLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false,
            columnDefinition = "ENUM('APPROVED','REJECTED','SUBMITTED')")
    private Action action;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @CreationTimestamp
    @Column(name = "actionDate", updatable = false)
    private LocalDateTime actionDate;

    // -------------------------------------------------------
    // Many approval histories belong to one travel request
    // -------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelReqID", referencedColumnName = "travelReqID",
            nullable = false)
    private TravelRequest travelRequest;

    // -------------------------------------------------------
    // Many approval histories belong to one approver (employee)
    // -------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approverID", referencedColumnName = "employeeID",
            nullable = false)
    private Employee approver;

    public enum ApprovalLevel {
        MANAGER, FINANCE
    }

    public enum Action {
        APPROVED, REJECTED, SUBMITTED
    }
}
