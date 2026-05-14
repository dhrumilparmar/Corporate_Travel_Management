package com.TravelManagement.Trial_101.travelRequests.Repository.ExpenseCategoryRepo;

import com.TravelManagement.Trial_101.travelRequests.Entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepo extends JpaRepository<ExpenseCategory, Integer> {
}
