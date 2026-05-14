package com.TravelManagement.Trial_101.travelRequests.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "request_budget")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budgetID")
    private Integer budgetID;

    @Column(name = "travelAmount",
            precision = 10, scale = 2,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private BigDecimal travelAmount = BigDecimal.ZERO;

    @Column(name = "accommodationAmount",
            precision = 10, scale = 2,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private BigDecimal accommodationAmount = BigDecimal.ZERO;

    @Column(name = "localTransportAmount",
            precision = 10, scale = 2,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private BigDecimal localTransportAmount = BigDecimal.ZERO;

    @Column(name = "mealsAmount",
            precision = 10, scale = 2,
            columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private BigDecimal mealsAmount = BigDecimal.ZERO;

    // Computed fields (calculated in Java or via DB trigger)
    @Column(name = "subTotal", precision = 10, scale = 2)
    private BigDecimal subTotal;

    @Column(name = "contingency", precision = 10, scale = 2)
    private BigDecimal contingency;

    @Column(name = "totalBudget", precision = 10, scale = 2)
    private BigDecimal totalBudget;

    // -------------------------------------------------------
    // One-to-One: One budget belongs to one travel request
    // -------------------------------------------------------
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelReqID",
            referencedColumnName = "travelReqID",
            unique = true,
            nullable = false)
    private TravelRequest travelRequest;

    // Auto-calculate before saving
    @PrePersist
    @PreUpdate
    public void calculateBudget() {
        BigDecimal sub = travelAmount
                .add(accommodationAmount)
                .add(localTransportAmount)
                .add(mealsAmount);
        this.subTotal    = sub;
        this.contingency = sub.multiply(new BigDecimal("0.05"));
        this.totalBudget = sub.multiply(new BigDecimal("1.05"));
    }
}
