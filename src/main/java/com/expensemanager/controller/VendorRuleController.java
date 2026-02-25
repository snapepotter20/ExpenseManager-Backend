package com.expensemanager.controller;

import com.expensemanager.dto.VendorRuleCreateRequest;
import com.expensemanager.dto.VendorRuleResponse;
import com.expensemanager.service.VendorCategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-rules")
@CrossOrigin(origins = "http://localhost:5173")
public class VendorRuleController {
    private final VendorCategoryService vendorCategoryService;

    public VendorRuleController(VendorCategoryService vendorCategoryService) {
        this.vendorCategoryService = vendorCategoryService;
    }

    @GetMapping
    public List<VendorRuleResponse> listRules() {
        return vendorCategoryService.getRules().stream().map(VendorRuleResponse::from).toList();
    }

    @PostMapping
    public VendorRuleResponse upsertRule(@Valid @RequestBody VendorRuleCreateRequest request) {
        return VendorRuleResponse.from(vendorCategoryService.upsertRule(request));
    }
}
