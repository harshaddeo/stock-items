package com.commercetools.stock.dto;

import java.time.ZonedDateTime;

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
    private ZonedDateTime timestamp;
    private String productId;
    private int quantity;
}
