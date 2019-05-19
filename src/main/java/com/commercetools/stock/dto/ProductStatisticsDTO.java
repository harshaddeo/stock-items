package com.commercetools.stock.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStatisticsDTO implements java.io.Serializable{

    private LocalDateTime requestTimestamp;
    private String range;
    private List<TopAvailableProduct> topAvailableProducts;
    private List<TopSellingProduct> topSellingProducts;
}
