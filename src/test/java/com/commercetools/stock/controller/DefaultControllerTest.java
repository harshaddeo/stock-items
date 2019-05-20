package com.commercetools.stock.controller;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.commercetools.stock.dto.ProductStatisticsDTO;
import com.commercetools.stock.dto.TimeSpan;
import com.commercetools.stock.model.Product;
import com.commercetools.stock.model.Stock;
import com.commercetools.stock.repository.ItemSoldRepository;
import com.commercetools.stock.repository.ProductRepository;
import com.commercetools.stock.repository.StockRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefaultControllerTest {


    @LocalServerPort
    protected int port;

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemSoldRepository itemSoldRepository;
    @Autowired
    private TestDataFactory testDataFactory;
    private Product product;
    private ProductStatisticsDTO statistics;
    private Stock stock;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @After
    public void after() {

    }



    @Test
    public void verifyGetProductEndpoint() {
        product = testDataFactory.getProduct();
        given()
                .param("productId", product.getProductId())
                .when()
                .get("/stock")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void verifyGetStatisticsEndPoint() {
        statistics = testDataFactory.getStats();
        given()
                .param("time", TimeSpan.TODAY)
                .when()
                .get("/statistics")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void verifyUpdateStockEndpointWithOutDatedStock() {
       stock = testDataFactory.getStock();

        given()
                .contentType(ContentType.JSON)
                .body(stock)
                .post("/updateStock")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void verifyUpdateStockEndpointNewStock() {
         stock = stockRepository.findAll().get(0);
        given()
                .contentType(ContentType.JSON)
                .body(stock)
                .post("/updateStock")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }
}
