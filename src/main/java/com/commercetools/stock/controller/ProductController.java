package com.commercetools.stock.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.stock.model.Product;
import com.commercetools.stock.model.ProductStatistics;
import com.commercetools.stock.model.Stock;
import com.commercetools.stock.model.TopAvailableProduct;
import com.commercetools.stock.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("/stock")
    public ResponseEntity getProduct(@RequestParam("productId") String productId) {
        Optional<Product> optionalProduct = productService.getProductById(productId);
        return optionalProduct.isPresent() ? ResponseEntity.ok(optionalProduct.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity getStatistics(@RequestParam("time") String span) {
        List<Stock> stocks;
        ProductStatistics statistics = new ProductStatistics();

        if ("today".equalsIgnoreCase(span))
            stocks = productService.getstatisticsForToday(span);
        else
            stocks = productService.getstatisticsForToday(span);

        List<TopAvailableProduct> topAvailableProductList = new ArrayList<>();
        stocks.forEach(stock ->
                topAvailableProductList.add(
                        TopAvailableProduct.builder()
                                .id(stock.getId())
                                .productId(stock.getProduct().getProductId())
                                .quantity(stock.getQuantity())
                                .timestamp(stock.getTimestamp())
                                .build()));

        statistics.setRange(span);
        statistics.setRequestTimestamp(LocalDateTime.now());
        statistics.setTopAvailableProducts(topAvailableProductList);
        return ResponseEntity.ok(statistics);
    }
}
