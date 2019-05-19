package com.commercetools.stock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="item_sold")
public class ItemSold {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int itemSoldId;
    private int itemsSold;
    private LocalDateTime itemSoldDate;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    @NotNull
    private Stock stock;

}
