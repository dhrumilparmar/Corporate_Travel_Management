package com.TravelManagement.Trial_101.travelRequests.Repository.ExpenseRepo;

import com.TravelManagement.Trial_101.travelRequests.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Expenserepository extends JpaRepository<Expense, Integer> {
}
