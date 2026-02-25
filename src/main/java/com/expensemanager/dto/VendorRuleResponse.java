package com.expensemanager.dto;

import com.expensemanager.model.Category;
import com.expensemanager.model.VendorCategoryRule;

public record VendorRuleResponse(Long id, String vendorName, Category category) {
    public static VendorRuleResponse from(VendorCategoryRule rule) {
        return new VendorRuleResponse(rule.getId(), rule.getVendorName(), rule.getCategory());
    }
}
