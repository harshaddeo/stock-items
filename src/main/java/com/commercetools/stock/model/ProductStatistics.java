package com.commercetools.stock.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatistics implements java.io.Serializable{

    private LocalDateTime requestTimestamp;
    private String range;
    private List<TopAvailableProduct> topAvailableProducts;
    private List<TopSellingProducts> topSellingProducts;
}
