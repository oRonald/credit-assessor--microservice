package br.com.microservices.creditassessor.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Card {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal baseLimit;
}
