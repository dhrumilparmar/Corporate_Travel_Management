package com.TravelManagement.Trial_101.finance.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

// Root response that contains EVERYTHING the page needs in ONE API call
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancePendingListDTO {

    // Top stats (4 stat cards)
    private FinanceDashboardStatsDTO stats;

    // Table rows
    private List<FinancePendingListDTO> requests;

    // Pagination ("SHOWING 4 OF 14 REQUESTS")
    private Integer currentPage;       // 1
    private Integer totalPages;        // 4 (14 requests / 4 per page)
    private Long    totalElements;     // 14
    private Integer pageSize;          // 4
    private Boolean isLastPage;        // false
}
