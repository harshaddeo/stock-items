package com.commercetools.stock.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commercetools.stock.dto.ProductDTO;
import com.commercetools.stock.dto.ProductStatisticsDTO;
import com.commercetools.stock.dto.TimeSpan;
import com.commercetools.stock.dto.TopAvailableProduct;
import com.commercetools.stock.model.Product;
import com.commercetools.stock.model.Stock;
import com.commercetools.stock.service.ProductService;

import lombok.AllArgsConstructor;
import springfox.documentation.swagger2.mappers.ModelMapper;

@RestController
@RequestMapping
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    ModelMapper modelMapper;

    @GetMapping("/stock")
    public ResponseEntity getProduct(@RequestParam("productId") String productId) {
        Optional<Product> optionalProduct = productService.getProductById(productId);
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

        List<TopAvailableProduct> topAvailableProductList = getTopAvailableProducts(productService.getTopAvailableProductsByTime(span));

        statistics.setRange(span.name());
        statistics.setRequestTimestamp(LocalDateTime.now(ZoneId.of("UTC")));
        statistics.setTopAvailableProducts(topAvailableProductList);
        return ResponseEntity.ok(statistics);
    }

    private List<TopAvailableProduct> getTopAvailableProducts(List<Stock> stocks) {
        List<TopAvailableProduct> topAvailableProductList = new ArrayList<>();
        stocks.forEach(stock ->
                topAvailableProductList.add(
                        TopAvailableProduct.builder()
                                .id(stock.getId())
                                .productId(stock.getProduct().getProductId())
                                .quantity(stock.getQuantity())
                                .timestamp(stock.getTimestamp())
                                .build()));
        return topAvailableProductList;
    }
}
