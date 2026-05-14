package com.TravelManagement.Trial_101.travelRequests.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "transport_mode")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transportID")
    private Integer transportID;

    @Enumerated(EnumType.STRING)
    @Column(name = "transportName", unique = true, nullable = false,
            columnDefinition = "ENUM('ROAD','TRAIN','FLIGHT')")
    private TransportName transportName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",
            columnDefinition = "ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE'")
    private Status status = Status.ACTIVE;

    // One transport mode has many policy_transports
    @OneToMany(mappedBy = "transportMode", fetch = FetchType.LAZY)
    private List<PolicyTransport> policyTransports;

    // One transport mode has many travel requests
    @OneToMany(mappedBy = "transportMode", fetch = FetchType.LAZY)
    private List<TravelRequest> travelRequests;

    public enum TransportName {
        ROAD, TRAIN, FLIGHT
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}
