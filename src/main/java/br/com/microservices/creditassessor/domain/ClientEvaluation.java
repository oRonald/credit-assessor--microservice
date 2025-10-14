package br.com.microservices.creditassessor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientEvaluation {

    private List<ApprovedCard> cards;
}
