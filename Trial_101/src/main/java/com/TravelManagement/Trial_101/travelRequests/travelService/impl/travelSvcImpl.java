package com.TravelManagement.Trial_101.travelRequests.travelService.impl;

import com.TravelManagement.Trial_101.employee.employeeRepository.employeeRepository;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseCreateRequest;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseItemRequest;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.TravelRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.*;
import com.TravelManagement.Trial_101.travelRequests.Entity.*;
import com.TravelManagement.Trial_101.travelRequests.Repository.ExpenseRepo.Expenserepository;
import com.TravelManagement.Trial_101.travelRequests.Repository.ExpenseCategoryRepo.ExpenseCategoryRepo;
import com.TravelManagement.Trial_101.travelRequests.Repository.travelRepository.TransportModeRepository;
import com.TravelManagement.Trial_101.travelRequests.Repository.travelRepository.TravelRepository;
import com.TravelManagement.Trial_101.travelRequests.travelService.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.TravelManagement.Trial_101.employee.Entity.Employee;

import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class travelSvcImpl implements Service {

    @Autowired
    TravelRepository travelRepo;

    @Autowired
    TransportModeRepository transportModeRepository;

    @Autowired
    Expenserepository expensrepo;

    @Autowired
    ExpenseCategoryRepo expenseCategoryRepo;

    @Autowired
    employeeRepository employeerepositoryl;

    @Override
    public ExpenseResponseDTO addExpense(ExpenseRequestDTO dto) {
        // 1. Fetch required entities by ID
        TravelRequest travelRequest = travelRepo.findById(dto.getTravelReqID())
                .orElseThrow(() -> new EntityNotFoundException("Travel Request not found"));


        Employee employee = employeerepositoryl.findById(dto.getEmployeeID())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"+ dto.getEmployeeID()));

        ExpenseCategory category = expenseCategoryRepo.findById(dto.getCategoryID())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        // 2. Create and Populate Expense Entity
        Expense expense = new Expense();
        expense.setExpenseCategory(category);
        expense.setTravelRequest(travelRequest);
        expense.setEmployee(employee);
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setReceiptFile(dto.getReceiptFile());

        // Set Enum status
        expense.setStatus(Expense.Status.PROCESSING);

        travelRequest.setStatus(TravelRequest.Status.REM_PENDING);
        // 3. Save
        Expense savedExpense = expensrepo.save(expense);

        // 4. Return Response DTO
        return mapToExpenseResponseDTO(savedExpense);
    }

    private ExpenseResponseDTO mapToExpenseResponseDTO(Expense expense) {
        return ExpenseResponseDTO.builder()
                .expenseID(expense.getExpenseID())
                .travelReqID(expense.getTravelRequest().getTravelReqID())
                .requestCode(expense.getTravelRequest().getRequestCode())
                .EmployeeID(expense.getEmployee().getEmployeeID())
                .EmployeeName(expense.getEmployee().getFullName())
                .CategoryID(expense.getExpenseCategory().getCategoryID())
                .CategoryName(expense.getExpenseCategory().getCategoryName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .receiptFile(expense.getReceiptFile())
                .expenseDate(expense.getExpenseDate())
                .status(expense.getStatus().name())
                .createdAt(expense.getCreatedAt())
                .build();
    }




    @Override
    public List<TravelRequestFinanceDTO> getAllfinApproved(Integer employeeid){
        return expensrepo.getAllfinanceApproved(employeeid);
    }



    @Override
    public List<TravelRequestResponseDTO> getAllRequestsByEmployeeId(Integer employeeID) {
        List<TravelRequest> entities = travelRepo.findByEmployee_EmployeeID(employeeID);

        return entities.stream()
                .map(this::mapToTravelRequestResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TravelRequestResponseDTO createRequest(TravelRequestDTO dto) {

        TravelRequest travelRequest = new TravelRequest();

        int currentYear = Year.now().getValue();
        String prefix = "TR-" + currentYear + "-";

        // 1. Ask the DB for the last code of this year
        String lastCode = travelRepo.findLastRequestCode(prefix);

        // 2. Calculate the next number
        int nextNumber = 1;
        if (lastCode != null) {
            // Extract the number part (e.g., "TR-2026-005" -> "005" -> 5)
            String lastNumberStr = lastCode.substring(prefix.length());
            nextNumber = Integer.parseInt(lastNumberStr) + 1;
        }

        // 3. Format it (e.g., "TR-2026-001") and SET IT IN JAVA
        travelRequest.setRequestCode(String.format("%s%03d", prefix, nextNumber));
        // ==========================================

        // Map Simple Fields
        travelRequest.setDestination(dto.getDestination());
        travelRequest.setStartTravel(dto.getStartTravel());
        travelRequest.setEndTravel(dto.getEndTravel());
        travelRequest.setPurpose(dto.getPurpose());
        travelRequest.setJustification(dto.getJustification());
        travelRequest.setPolicyViolation(false);
//        travelRequest.setRequestBudget(dto.getBudget().);

        if (dto.getStatus() != null) {
            travelRequest.setStatus(TravelRequest.Status.valueOf(dto.getStatus().toUpperCase()));
        } else {
            travelRequest.setStatus(TravelRequest.Status.DRAFT);
        }

        // Map Employee (Bulletproof)
        Integer empId = dto.getEmployeeID() != null ? dto.getEmployeeID() : 6; // Fallback to 6
        Employee emp = new Employee();
        emp.setEmployeeID(empId);
        travelRequest.setEmployee(emp);

        // Map Transport Mode
        if (dto.getTransportID() != null) {
            TransportMode transport = new TransportMode();
            transport.setTransportID(dto.getTransportID());
            travelRequest.setTransportMode(transport);
        }

        // Map Budget
        if (dto.getBudget() != null) {
            RequestBudget budget = new RequestBudget();
            budget.setTravelAmount(dto.getBudget().getTravelAmount());
            budget.setAccommodationAmount(dto.getBudget().getAccommodationAmount());
            budget.setLocalTransportAmount(dto.getBudget().getLocalTransportAmount());
            budget.setMealsAmount(dto.getBudget().getMealsAmount());

            budget.setTravelRequest(travelRequest);
            travelRequest.setRequestBudget(budget);
        }

        // 💾 SAVE TO DATABASE (Hibernate is happy because requestCode is NO LONGER NULL)
        TravelRequest savedRequest = travelRepo.save(travelRequest);

        return mapToTravelRequestResponseDTO(savedRequest);
    }

    @Override
    public void deleteRequest(Integer reqID){

        if (!travelRepo.existsById(reqID)) {
            // Or just let deleteById throw the error, which is also fine
            throw new EntityNotFoundException("Travel Request not found with id: " + reqID);
        }

        travelRepo.deleteById(reqID);

    }
    @Override
    public TravelRequestResponseDTO updateRequest(TravelRequestDTO dto) {

        // Safety Check: Verify ID was successfully mapped from JSON
        if (dto.getTravelReqID() == null) {
            throw new IllegalArgumentException("travelReqID is missing or invalid in the request body");
        }

        // 1. Fetch existing entity
        TravelRequest existing = travelRepo.findById(dto.getTravelReqID())
                .orElseThrow(() -> new EntityNotFoundException("Travel request not found with ID: " + dto.getTravelReqID()));

        // 2. Safely verify status is DRAFT (Handles both Enum and String statuses)
        String currentStatus = existing.getStatus() != null ? existing.getStatus().toString() : "";

        if (!"DRAFT".equalsIgnoreCase(currentStatus)) {
            throw new IllegalStateException("Only DRAFT requests can be updated. Current status is: " + currentStatus);
        }

        // 3. Update standard fields
        existing.setDestination(dto.getDestination());
        existing.setStartTravel(dto.getStartTravel());
        existing.setEndTravel(dto.getEndTravel());
        existing.setPurpose(dto.getPurpose());
        existing.setJustification(dto.getJustification());


        if (dto.getTransportID() != null) {
            TransportMode transport = transportModeRepository.findById(dto.getTransportID())
                    .orElseThrow(() -> new EntityNotFoundException("Transport not found"));
            existing.setTransportMode(transport);
        }
        // Safely update Status Enum (e.g., moving from DRAFT to SUBMITTED)
        if (dto.getStatus() != null) {
            existing.setStatus(TravelRequest.Status.valueOf(dto.getStatus().toUpperCase()));
        }

        // Update Nested Budget Values
        if (dto.getBudget() != null && existing.getRequestBudget() != null) {
            existing.getRequestBudget().setTravelAmount(dto.getBudget().getTravelAmount());
            existing.getRequestBudget().setAccommodationAmount(dto.getBudget().getAccommodationAmount());
            existing.getRequestBudget().setLocalTransportAmount(dto.getBudget().getLocalTransportAmount());
            existing.getRequestBudget().setMealsAmount(dto.getBudget().getMealsAmount());
        }

        // 4. Save the updated entity
        TravelRequest saved = travelRepo.save(existing);

        // 5. Return response
        return mapToTravelRequestResponseDTO(saved);
    }



    private TravelRequestResponseDTO mapToTravelRequestResponseDTO(TravelRequest travelRequest) {
        if (travelRequest == null) return null;

        TravelRequestResponseDTO dto = new TravelRequestResponseDTO();

        // 1. Map Basic Fields
        dto.setTravelReqID(travelRequest.getTravelReqID());
        dto.setRequestCode(travelRequest.getRequestCode()); // Generated by MySQL Trigger!
        dto.setDestination(travelRequest.getDestination());
        dto.setStartTravel(travelRequest.getStartTravel());
        dto.setEndTravel(travelRequest.getEndTravel());
        dto.setPurpose(travelRequest.getPurpose());
        dto.setJustification(travelRequest.getJustification());
        dto.setPolicyViolation(travelRequest.getPolicyViolation());
        dto.setCreatedAt(travelRequest.getCreatedAt());
        dto.setUpdatedAt(travelRequest.getUpdatedAt());
        dto.setEmployeeID(travelRequest.getEmployee().getEmployeeID());
//        dto.getEmployeeID();

            // 2. Map Enum to String safely
        if (travelRequest.getStatus() != null) {
            dto.setStatus(travelRequest.getStatus().name());
        }

        // 3. 🧮 COMPUTED FIELD: Calculate Trip Days dynamically
        if (travelRequest.getStartTravel() != null && travelRequest.getEndTravel() != null) {
            long days = ChronoUnit.DAYS.between(travelRequest.getStartTravel(), travelRequest.getEndTravel());
            dto.setTripDays((int) days);
        }

        // 4. Map Nested Objects using Helper Methods
//        dto.setEmployee(mapToEmployeeSummaryDTO(travelRequest.getEmployee()));
        dto.setTransportMode(mapToTransportModeResponseDTO(travelRequest.getTransportMode()));
        dto.setBudget(mapToRequestBudgetResponseDTO(travelRequest.getRequestBudget()));

        return dto;
    }

    private RequestBudgetResponseDTO mapToRequestBudgetResponseDTO(RequestBudget budget) {
        if (budget == null) return null;

        RequestBudgetResponseDTO dto = new RequestBudgetResponseDTO();
        dto.setBudgetID(budget.getBudgetID());
        dto.setTravelAmount(budget.getTravelAmount());
        dto.setAccommodationAmount(budget.getAccommodationAmount());
        dto.setLocalTransportAmount(budget.getLocalTransportAmount());
        dto.setMealsAmount(budget.getMealsAmount());

        // These were auto-calculated by @PrePersist in the Entity!
        dto.setSubTotal(budget.getSubTotal());
        dto.setContingency(budget.getContingency());
        dto.setTotalBudget(budget.getTotalBudget());

        return dto;
    }

    private TransportModeResponseDTO mapToTransportModeResponseDTO(TransportMode transportMode) {
        if (transportMode == null) return null;

        TransportModeResponseDTO dto = new TransportModeResponseDTO();
        dto.setTransportID(transportMode.getTransportID());
        dto.setTransportName(transportMode.getTransportName() != null ? transportMode.getTransportName().name() : null);
        dto.setStatus(transportMode.getStatus() != null ? transportMode.getStatus().name() : null);

        return dto;
    }


    //prac save expense


    public List<ExpenseResponse> saveExpenses(ExpenseCreateRequest request) {

        TravelRequest travelRequest = travelRepo.findById(request.getTravelRequestId())
                .orElseThrow(() -> new RuntimeException("Travel Request not found"+ request.getTravelRequestId()));

        Employee employee = employeerepositoryl.findById(request.getEmployeeId())  // fixed method name
                .orElseThrow(() -> new RuntimeException("Employee not found" + request.getEmployeeId()));

        // Validate ownership (good practice)
        if (!travelRequest.getEmployee().getEmployeeID().equals(employee.getEmployeeID())) {
            throw new RuntimeException("Employee does not own this travel request");
        }

        // Delete old expenses if you want to replace all (uncomment if needed)
        // expensrepo.deleteByTravelRequest_TravelReqID(request.getTravelRequestId());

        List<Expense> expenseList = request.getExpenses().stream()
                .map(item -> convertToEntity(item, travelRequest, employee))   // ← Pass employee here
                .toList();

        List<Expense> savedExpenses = expensrepo.saveAll(expenseList);

        return savedExpenses.stream()
                .map(this::convertToResponse)
                .toList();
    }

    private Expense convertToEntity(ExpenseItemRequest item,
                                    TravelRequest travelRequest,
                                    Employee employee) {

        Expense expense = new Expense();

        expense.setTravelRequest(travelRequest);

        expense.setEmployee(employee);                    // ← This was missing
        expense.setAmount(item.getAmount());
        expense.setExpenseDate(item.getDate());           // corrected field name
        expense.setDescription(item.getDescription());

        // Set category
        var category = expenseCategoryRepo.findById(item.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        expense.setExpenseCategory(category);

        // receipt is boolean in DTO, but receiptFile is String in entity
        expense.setReceiptFile(item.getReceipt() != null && item.getReceipt() ? "receipt_uploaded" : null);

//        expense.setStatus(Expense.Status.PROCESSING);
        travelRequest.setStatus(TravelRequest.Status.REM_PENDING);

        return expense;
    }
    private ExpenseResponse convertToResponse(Expense expense) {
        return ExpenseResponse.builder()
                .amount(expense.getAmount())
                .categoryName(expense.getExpenseCategory().getCategoryName())
                .categoryId(expense.getExpenseCategory().getCategoryID())
                .date(expense.getExpenseDate())
                .description(expense.getDescription())
//                .receipt(expense.getReceiptFile() != null)
                .receipt(expense.getReceiptFile())// return boolean if your DTO expects it
                .createdAt(expense.getCreatedAt())
                .expenseId(expense.getExpenseID())
                .build();
    }

}
