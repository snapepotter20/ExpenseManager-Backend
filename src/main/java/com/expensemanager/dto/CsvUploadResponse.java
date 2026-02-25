package com.expensemanager.dto;

import java.util.List;

public record CsvUploadResponse(int importedCount, List<ExpenseResponse> expenses) {
}
