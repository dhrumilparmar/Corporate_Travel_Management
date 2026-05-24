package com.TravelManagement.Trial_101.manager.DTO.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalRequestDetailDTO {

    // Top header employee card
    private String employeeName;
    private String employeeDesignation;
    private String employeeAvatar;

    // Main request details
    private String requestCode;
    private String destination;
    private String startTravel;
    private String endTravel;
    private String purpose;
    private BigDecimal totalBudget;
    private String policyStatus;
    private Boolean policyCompliant;

    // Optional expandable budget breakdown
    private BigDecimal travelAmount;
    private BigDecimal accommodationAmount;
    private BigDecimal mealsAmount;
    private BigDecimal localTransportAmount;
}