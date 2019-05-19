package com.commercetools.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commercetools.stock.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>/*, QuerydslPredicateExecutor<Product> */{

    /*List<Product> findAllByRequestTimeStamp(Timestamp timestamp);*/

    /*@Query("select product_id, request_timestamp, stock_id from product p where p.request_timestamp <= :request_timestamp")
    List<Product> findAllWithRequestTimestampLastMonth(
            @Param("request_timestamp") Timestamp request_timestamp);*/

}
