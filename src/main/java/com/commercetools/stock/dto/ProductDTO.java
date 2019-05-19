package com.commercetools.stock.dto;

import java.time.LocalDateTime;

import com.commercetools.stock.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Product product;
    private LocalDateTime requestTimeStamp;

}
