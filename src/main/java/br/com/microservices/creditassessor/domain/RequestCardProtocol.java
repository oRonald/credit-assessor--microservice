package br.com.microservices.creditassessor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestCardProtocol {

    private String protocol;
}
