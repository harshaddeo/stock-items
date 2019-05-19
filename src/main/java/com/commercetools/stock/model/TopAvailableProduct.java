package com.commercetools.stock.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TopAvailableProduct {

    private String id;
    private LocalDateTime timestamp;
    private String productId;
    private int quantity;



}
