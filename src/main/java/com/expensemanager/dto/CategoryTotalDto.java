package com.expensemanager.dto;

import com.expensemanager.model.Category;

import java.math.BigDecimal;

public record CategoryTotalDto(Category category, BigDecimal total) {
}
