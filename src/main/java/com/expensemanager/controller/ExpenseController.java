package com.expensemanager.controller;

import com.expensemanager.dto.CsvUploadResponse;
import com.expensemanager.dto.DashboardResponse;
import com.expensemanager.dto.ExpenseCreateRequest;
import com.expensemanager.dto.ExpenseResponse;
import com.expensemanager.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseResponse createExpense(@Valid @RequestBody ExpenseCreateRequest request) {
        return expenseService.createExpense(request);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CsvUploadResponse uploadCsv(@RequestPart("file") MultipartFile file) throws IOException {
        return expenseService.uploadCsv(file);
    }

    @GetMapping
    public List<ExpenseResponse> listExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(@RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return expenseService.getDashboard(month);
    }
}
