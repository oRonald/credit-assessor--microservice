package br.com.microservices.creditassessor.application;

import br.com.microservices.creditassessor.domain.ClientCard;
import br.com.microservices.creditassessor.domain.ClientData;
import br.com.microservices.creditassessor.domain.ClientSituation;
import br.com.microservices.creditassessor.infrastructure.CardsResourceClient;
import br.com.microservices.creditassessor.infrastructure.ClientResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditAssessorService {

    private final ClientResourceClient resourceClient;
    private final CardsResourceClient cardsResourceClient;

    public ClientSituation getClientSituation(String cpf){
        ResponseEntity<ClientData> clientDataResponse = resourceClient.getClientSituation(cpf);
        ResponseEntity<List<ClientCard>> cardsResponse = cardsResourceClient.cardByClients(cpf);
        return ClientSituation.builder()
                .client(clientDataResponse.getBody())
                .cards(cardsResponse.getBody())
                .build();
    }

}
