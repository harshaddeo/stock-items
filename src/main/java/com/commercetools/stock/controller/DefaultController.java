package com.commercetools.stock.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.stock.dto.ProductDTO;
import com.commercetools.stock.dto.ProductStatisticsDTO;
import com.commercetools.stock.dto.TimeSpan;
import com.commercetools.stock.dto.TopAvailableProduct;
import com.commercetools.stock.dto.TopSellingProduct;
import com.commercetools.stock.model.Product;
import com.commercetools.stock.model.Stock;
import com.commercetools.stock.service.DefaultService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class DefaultController {

    private DefaultService defaultService;

    @GetMapping("/stock")
    public ResponseEntity getProduct(@RequestParam("productId") String productId) {
        Optional<Product> optionalProduct = defaultService.getProductById(productId);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(
                    ProductDTO.builder()
                    .product(optionalProduct.get())
                    .requestTimeStamp(LocalDateTime.now(ZoneId.of("UTC")))
                    .build());
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity getStatistics(@RequestParam("time") TimeSpan span) {
        ProductStatisticsDTO statistics = new ProductStatisticsDTO();

        List<TopAvailableProduct> topAvailableProductList = defaultService.getTopAvailableProducts(span);
        List<TopSellingProduct> topSellingProductList = defaultService.getTopSellingProducts(span);

        statistics.setRange(span.name());
        statistics.setRequestTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
        statistics.setTopAvailableProducts(topAvailableProductList);
        statistics.setTopSellingProducts(topSellingProductList);

        return ResponseEntity.ok(statistics);
    }



    @PostMapping("/updateStock")
    public ResponseEntity updateStock(@RequestBody Stock stock){

        if (null != defaultService.updateStockItems(stock)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
