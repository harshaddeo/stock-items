package com.commercetools.stock.model;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stock")
@Builder
public class Stock {

    @Id
    private String id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss Z", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss Z")
    private ZonedDateTime timestamp;

    @NotNull
    private int quantity;

    @OneToOne (mappedBy="stock")
    @JsonIgnore
    private Product product;

}
