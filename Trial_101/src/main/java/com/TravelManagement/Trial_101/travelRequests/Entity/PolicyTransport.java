package com.TravelManagement.Trial_101.travelRequests.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "policy_transport",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "policy_transport_index_5",
                        columnNames = {"policyID", "transportID"}
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // -------------------------------------------------------
    // Many policy transports belong to one travel policy
    // -------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policyID", referencedColumnName = "policyID",
            nullable = false)
    private TravelPolicy travelPolicy;

    // -------------------------------------------------------
    // Many policy transports belong to one transport mode
    // -------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportID", referencedColumnName = "transportID",
            nullable = false)
    private TransportMode transportMode;
}
