package com.expensemanager.repository;

import com.expensemanager.model.Category;
import com.expensemanager.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT AVG(e.amount) FROM Expense e WHERE e.category = :category")
    BigDecimal averageByCategory(@Param("category") Category category);

    @Query("SELECT e.category as category, SUM(e.amount) as total FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate GROUP BY e.category")
    List<CategoryTotalProjection> monthlyTotalsByCategory(@Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);

    @Query("SELECT e.vendorName as vendorName, SUM(e.amount) as total FROM Expense e GROUP BY e.vendorName ORDER BY SUM(e.amount) DESC")
    List<VendorTotalProjection> vendorTotals();

    List<Expense> findByAnomalyTrueOrderByDateDesc();

    interface CategoryTotalProjection {
        Category getCategory();
        BigDecimal getTotal();
    }

    interface VendorTotalProjection {
        String getVendorName();
        BigDecimal getTotal();
    }
}
