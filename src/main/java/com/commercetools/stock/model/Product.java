package com.commercetools.stock.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String productId;

    @NotNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;


}
