package com.expensemanager.dto;

import com.expensemanager.model.Category;
import com.expensemanager.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        LocalDate date,
        BigDecimal amount,
        String vendorName,
        String description,
        Category category,
        boolean anomaly
) {
    public static ExpenseResponse from(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getDate(),
                expense.getAmount(),
                expense.getVendorName(),
                expense.getDescription(),
                expense.getCategory(),
                expense.isAnomaly()
        );
    }
}
