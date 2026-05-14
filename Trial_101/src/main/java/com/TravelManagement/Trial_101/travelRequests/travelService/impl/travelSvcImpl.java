package com.TravelManagement.Trial_101.travelRequests.travelService.impl;

import com.TravelManagement.Trial_101.employee.employeeRepository.employeeRepository;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.ExpenseRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.RequestDTO.TravelRequestDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.ExpenseResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.RequestBudgetResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TransportModeResponseDTO;
import com.TravelManagement.Trial_101.travelRequests.DTO.ResponseDTO.TravelRequestResponseDTO;
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
    public List<TravelRequestResponseDTO> getAllRequest() {

        // 1. Fetch ENTITIES from the database (Using the built-in findAll method)
        List<TravelRequest> entities = travelRepo.findAll();

        // 2. Create an empty list for DTOs
        List<TravelRequestResponseDTO> dtoList = new ArrayList<>();

        // 3. Loop through the ENTITIES
        for (TravelRequest entity : entities) {
            // 4. Convert Entity to DTO and add to the DTO list
            dtoList.add(mapToTravelRequestResponseDTO(entity));
        }

        // 5. Return the populated DTO list
        return dtoList;
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
    public void deleteRequest(TravelRequest travelRequest){
           if (travelRequest.getTravelReqID() != null ){
               int reqId = travelRequest.getTravelReqID();
               travelRepo.deleteById(reqId);
           }

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


    @Override
    public ExpenseResponseDTO submitBills(ExpenseRequestDTO dto) {
        System.out.println("RECEIVED DTO: " + dto.toString());


        // 1. Create the Entity
        Expense expense = new Expense();

        // 2. Map Simple Fields
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setReceiptFile(dto.getReceiptFile());

        // Set default status to PROCESSING (Finance hasn't approved it yet)
        expense.setStatus(Expense.Status.PROCESSING);

        // 3. Map Foreign Keys (The "Dummy Object" Trick)
        // Link to Travel Request
        if (dto.getTravelReqID() != null) {
            TravelRequest tr = new TravelRequest();
            tr.setTravelReqID(dto.getTravelReqID());
            expense.setTravelRequest(tr);
        } else {
            throw new IllegalArgumentException("Travel Request ID is required to submit an expense!");
        }

        // Link to Employee
        Integer empId = dto.getEmployeeID() != null ? dto.getEmployeeID() : 1; // Fallback for testing
        Employee emp = new Employee();
        emp.setEmployeeID(empId);
        expense.setEmployee(emp);

        // Link to Expense Category
        ExpenseCategory catStub = new ExpenseCategory();
        catStub.setCategoryID(dto.getCategoryID());
        expense.setExpenseCategory(catStub);

        // 4. Save to Database
        Expense savedExpense = expensrepo.save(expense);

        TravelRequest fullTravelReq = travelRepo.findById(dto.getTravelReqID()).orElse(null);
        Employee fullEmployee = employeerepositoryl.findById(emp.getEmployeeID()).orElse(null);
        ExpenseCategory fullCategory = expenseCategoryRepo.findById(dto.getCategoryID()).orElse(null);

        // 5. Map to Response DTO and return
        return mapToExpenseResponseDTO(savedExpense, fullTravelReq, fullEmployee, fullCategory);
    }


    private ExpenseResponseDTO mapToExpenseResponseDTO(Expense expense,
                                                       TravelRequest travelReq,
                                                       Employee employee,
                                                       ExpenseCategory category) {
        if (expense == null) return null;

        ExpenseResponseDTO dto = new ExpenseResponseDTO();
        dto.setExpenseID(expense.getExpenseID());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setExpenseDate(expense.getExpenseDate());
        dto.setReceiptFile(expense.getReceiptFile());
        dto.setStatus(expense.getStatus() != null ? expense.getStatus().name() : null);
        dto.setCreatedAt(expense.getCreatedAt());

        // ✅ Map from Full Entities (Now populated!)
        if (travelReq != null) {
            dto.setTravelReqID(travelReq.getTravelReqID());
            dto.setRequestCode(travelReq.getRequestCode()); // ✅ Now has value
        }

        if (employee != null) {
            dto.setEmployeeID(employee.getEmployeeID());
            dto.setEmployeeName(employee.getFullName()); // ✅ Now has value
        }

        if (category != null) {
            dto.setCategoryID(category.getCategoryID());
            dto.setCategoryName(category.getCategoryName()); // ✅ Now has value
        }

        return dto;
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

}
