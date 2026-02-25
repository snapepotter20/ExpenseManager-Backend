package com.expensemanager.dto;

import java.math.BigDecimal;

public record VendorTotalDto(String vendorName, BigDecimal total) {
}
