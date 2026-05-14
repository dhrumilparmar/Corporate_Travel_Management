package com.TravelManagement.Trial_101.travelRequests.Entity;


//import com.TravelManagement.Trial_101.employee.Entity.Designation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "travel_policy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policyID")
    private Integer policyID;

    @Column(name = "maxBudget", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxBudget;

    @Column(name = "policyName", length = 100)
    private String policyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            columnDefinition = "ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE'")
    private Status status = Status.ACTIVE;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    // -------------------------------------------------------
    // Many travel policies belong to one designation
    // -------------------------------------------------------
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "designationID", referencedColumnName = "designationID",
//            nullable = false)
//    private Designation designation;

    // -------------------------------------------------------
    // One travel policy has many policy transports
    // -------------------------------------------------------
    @OneToMany(mappedBy = "travelPolicy",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<PolicyTransport> policyTransports;

    public enum Status {
        ACTIVE, INACTIVE
    }
}