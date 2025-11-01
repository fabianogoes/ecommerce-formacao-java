package com.ecommerce.api.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record TotalSalesDTO(
        BigDecimal totalSales
) {
    public static TotalSalesDTO fromTotalSales(double totalSales) {
        BigDecimal totalRound = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
        return new TotalSalesDTO(totalRound);
    }
}
