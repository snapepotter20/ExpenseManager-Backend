package com.expensemanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor_category_rules")
public class VendorCategoryRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vendorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    public VendorCategoryRule() {
    }

    public VendorCategoryRule(String vendorName, Category category) {
        this.vendorName = vendorName;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
