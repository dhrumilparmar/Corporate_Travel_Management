package com.TravelManagement.Trial_101.travelRequests.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "expense_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryID")
    private Integer categoryID;

    @Column(name = "categoryName", unique = true, nullable = false, length = 50)
    private String categoryName;

    // One category has many expenses
    @OneToMany(mappedBy = "expenseCategory", fetch = FetchType.LAZY)
    private List<Expense> expenses;
}
