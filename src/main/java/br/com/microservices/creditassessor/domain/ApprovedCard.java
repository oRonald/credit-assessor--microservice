package br.com.microservices.creditassessor.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovedCard {

    private String card;
    private String brand;
    private BigDecimal approvedLimit;
}
