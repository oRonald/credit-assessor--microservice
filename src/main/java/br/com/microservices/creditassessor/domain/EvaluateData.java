package br.com.microservices.creditassessor.domain;

import lombok.Data;

@Data
public class EvaluateData {

    private String cpf;
    private Long income;
}
