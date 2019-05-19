package com.commercetools.stock.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commercetools.stock.model.Product;
import com.commercetools.stock.model.Stock;
import com.commercetools.stock.dto.TimeSpan;
import com.commercetools.stock.repository.ProductRepository;
import com.commercetools.stock.repository.StockRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> getProductById(String productId){
        return productRepository.findById(productId);
    }

    public List<Stock> getTopAvailableProductsByTime(TimeSpan timespan){

        Timestamp timestamp = null;
        if(timespan == TimeSpan.TODAY)
            timestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        else
            timestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIDNIGHT));
        return stockRepository.findAllByRequestTimeStamp(timestamp);

    }
}
