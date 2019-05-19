package com.commercetools.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commercetools.stock.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

    Product findProductByStock_Id(String stockId);

}
