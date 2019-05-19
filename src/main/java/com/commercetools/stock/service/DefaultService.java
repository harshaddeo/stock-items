package com.commercetools.stock.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercetools.stock.dto.TimeSpan;
import com.commercetools.stock.dto.TopAvailableProduct;
import com.commercetools.stock.dto.TopSellingProduct;
import com.commercetools.stock.model.ItemSold;
import com.commercetools.stock.model.Product;
import com.commercetools.stock.model.Stock;
import com.commercetools.stock.repository.ItemSoldRepository;
import com.commercetools.stock.repository.ProductRepository;
import com.commercetools.stock.repository.StockRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DefaultService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemSoldRepository itemSoldRepository;

    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    public List<TopSellingProduct> getTopSellingProducts(TimeSpan span) {
        Timestamp timestamp = getTimeStampBySpan(span);
        List<TopSellingProduct> topSellingProductList = new ArrayList<>();
        itemSoldRepository.findAllByItemSold(timestamp)
                .stream().
                forEach(nameOnly ->
                        topSellingProductList.add(TopSellingProduct.builder()
                                .productId(nameOnly.getProductId())
                                .itemsSold(nameOnly.getItemsSold())
                                .build()));

            return topSellingProductList;
    }

    public List<TopAvailableProduct> getTopAvailableProducts(TimeSpan span) {
        Timestamp timestamp = getTimeStampBySpan(span);
        List<Stock> stocks = stockRepository.findAllByRequestTimeStamp(timestamp);
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


    private Timestamp getTimeStampBySpan(TimeSpan timespan) {

        if (timespan == TimeSpan.TODAY)
            return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        else
            return Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIDNIGHT));
    }


    public Stock updateStockItems(Stock stockInput) {

        Optional<Stock> optionalStock = stockRepository.findById(stockInput.getId());
        if (!optionalStock.isPresent()) {
            return null;
        }

        int existingStockQuantity = optionalStock.get().getQuantity();
        int itemsSold = 0;

        if (stockInput.getQuantity() < existingStockQuantity) {
            itemsSold = existingStockQuantity - stockInput.getQuantity();
            itemSoldRepository.save(ItemSold.builder()
                    .itemsSold(itemsSold)
                    .stock(stockInput)
                    .itemSoldDate(stockInput.getTimestamp().toLocalDateTime())
                    .build());
        }
        return stockRepository.save(stockInput);
    }


}
