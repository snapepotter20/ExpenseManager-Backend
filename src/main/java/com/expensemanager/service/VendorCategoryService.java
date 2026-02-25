package com.expensemanager.service;

import com.expensemanager.dto.VendorRuleCreateRequest;
import com.expensemanager.model.Category;
import com.expensemanager.model.VendorCategoryRule;
import com.expensemanager.repository.VendorCategoryRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorCategoryService {
    private final VendorCategoryRuleRepository vendorCategoryRuleRepository;

    public VendorCategoryService(VendorCategoryRuleRepository vendorCategoryRuleRepository) {
        this.vendorCategoryRuleRepository = vendorCategoryRuleRepository;
    }

    public Category categoryForVendor(String vendorName) {
        return vendorCategoryRuleRepository
                .findByVendorNameIgnoreCase(vendorName)
                .map(VendorCategoryRule::getCategory)
                .orElse(Category.OTHER);
    }

    public VendorCategoryRule upsertRule(VendorRuleCreateRequest request) {
        VendorCategoryRule rule = vendorCategoryRuleRepository
                .findByVendorNameIgnoreCase(request.getVendorName().trim())
                .orElseGet(VendorCategoryRule::new);

        rule.setVendorName(request.getVendorName().trim());
        rule.setCategory(request.getCategory());
        return vendorCategoryRuleRepository.save(rule);
    }

    public List<VendorCategoryRule> getRules() {
        return vendorCategoryRuleRepository.findAll();
    }
}
