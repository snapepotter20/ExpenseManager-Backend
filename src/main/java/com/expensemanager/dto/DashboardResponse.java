package com.expensemanager.dto;

import java.util.List;

public record DashboardResponse(
        List<CategoryTotalDto> monthlyCategoryTotals,
        List<VendorTotalDto> topVendors,
        long anomalyCount,
        List<ExpenseResponse> anomalies
) {
}
