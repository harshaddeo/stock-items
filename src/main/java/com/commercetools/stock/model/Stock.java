package com.commercetools.stock.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stock")
public class Stock {

    @Id
    private String id;

    private LocalDateTime timestamp;

    @NotNull
    private int quantity;

    @OneToOne (mappedBy="stock")
    private Product product;

}
