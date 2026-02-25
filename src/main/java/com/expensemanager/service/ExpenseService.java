package com.expensemanager.service;

import com.expensemanager.dto.*;
import com.expensemanager.model.Category;
import com.expensemanager.model.Expense;
import com.expensemanager.repository.ExpenseRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final VendorCategoryService vendorCategoryService;

    public ExpenseService(ExpenseRepository expenseRepository, VendorCategoryService vendorCategoryService) {
        this.expenseRepository = expenseRepository;
        this.vendorCategoryService = vendorCategoryService;
    }

    public ExpenseResponse createExpense(ExpenseCreateRequest request) {
        Category category = vendorCategoryService.categoryForVendor(request.getVendorName());
        Expense expense = new Expense(
                request.getDate(),
                request.getAmount(),
                request.getVendorName().trim(),
                request.getDescription().trim(),
                category
        );

        expense.setAnomaly(isAnomaly(expense.getAmount(), category));
        return ExpenseResponse.from(expenseRepository.save(expense));
    }

    public CsvUploadResponse uploadCsv(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser parser = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setTrim(true).build().parse(reader)) {

            List<ExpenseResponse> imported = parser.stream()
                    .map(this::recordToRequest)
                    .map(this::createExpense)
                    .toList();

            return new CsvUploadResponse(imported.size(), imported);
        }
    }

    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll().stream().map(ExpenseResponse::from).toList();
    }

    public DashboardResponse getDashboard(YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        List<CategoryTotalDto> monthlyTotals = expenseRepository.monthlyTotalsByCategory(startDate, endDate)
                .stream()
                .map(row -> new CategoryTotalDto(row.getCategory(), row.getTotal()))
                .toList();

        List<VendorTotalDto> topVendors = expenseRepository.vendorTotals()
                .stream()
                .limit(5)
                .map(row -> new VendorTotalDto(row.getVendorName(), row.getTotal()))
                .toList();

        List<ExpenseResponse> anomalies = expenseRepository.findByAnomalyTrueOrderByDateDesc()
                .stream()
                .map(ExpenseResponse::from)
                .toList();

        return new DashboardResponse(monthlyTotals, topVendors, anomalies.size(), anomalies);
    }

    private ExpenseCreateRequest recordToRequest(CSVRecord record) {
        ExpenseCreateRequest request = new ExpenseCreateRequest();
        request.setDate(LocalDate.parse(record.get("date")));
        request.setAmount(new BigDecimal(record.get("amount")));
        request.setVendorName(record.get("vendorName"));
        request.setDescription(record.get("description"));
        return request;
    }

    private boolean isAnomaly(BigDecimal amount, Category category) {
        BigDecimal average = expenseRepository.averageByCategory(category);
        if (average == null || average.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        return amount.compareTo(average.multiply(BigDecimal.valueOf(3))) > 0;
    }
}
