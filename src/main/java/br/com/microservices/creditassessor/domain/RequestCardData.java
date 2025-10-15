package br.com.microservices.creditassessor.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RequestCardData {

    private Long cardId;
    private String cpf;
    private String address;
    private BigDecimal cardLimit;
}
