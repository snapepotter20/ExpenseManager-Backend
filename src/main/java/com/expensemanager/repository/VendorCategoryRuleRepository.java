package com.expensemanager.repository;

import com.expensemanager.model.VendorCategoryRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorCategoryRuleRepository extends JpaRepository<VendorCategoryRule, Long> {
    Optional<VendorCategoryRule> findByVendorNameIgnoreCase(String vendorName);
}
