package com.commercetools.stock.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.commercetools.stock.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

    @Query(value = "select * from Stock s where s.timestamp >= :value order by s.quantity desc LIMIT 3", nativeQuery = true)
    List<Stock> findAllByRequestTimeStamp(@Param("value") Timestamp timestamp);
}


