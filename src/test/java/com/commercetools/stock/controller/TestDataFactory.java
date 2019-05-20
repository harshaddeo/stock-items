package com.commercetools.stock.controller;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collections;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commercetools.stock.dto.ProductStatisticsDTO;
import com.commercetools.stock.dto.TopAvailableProduct;
import com.commercetools.stock.dto.TopSellingProduct;
import com.commercetools.stock.model.Product;
import com.commercetools.stock.model.Stock;
import com.commercetools.stock.repository.ItemSoldRepository;
import com.commercetools.stock.repository.ProductRepository;
import com.commercetools.stock.repository.StockRepository;

@Component
public class TestDataFactory {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemSoldRepository itemSoldRepository;


    @NotNull
    public Product getProduct() {
        return Product.builder()
                .productId("milk-002")
                .stock(getStock())
                .build();
    }

    @NotNull
    public Stock getStock() {
        return Stock.builder()
                .id("001")
                .quantity(500)
                .timestamp(ZonedDateTime.now())
                .product(new Product())
                .build();
    }

    @NotNull
    public ProductStatisticsDTO getStats(){

        TopAvailableProduct top  = TopAvailableProduct.builder()
                .id("001")
                .productId("milk-002")
                .quantity(500)
                .timestamp(ZonedDateTime.now()).build();
        TopSellingProduct topselling= TopSellingProduct.builder()
                .itemsSold(400)
                .productId("milk-002")
                .build();

        return ProductStatisticsDTO.builder()
                .range("TODAY")
                .requestTimestamp(LocalDateTime.now())
                .topAvailableProducts(Collections.singletonList(top))
                 .topSellingProducts(Collections.singletonList(topselling))
                .build();
    }
}
