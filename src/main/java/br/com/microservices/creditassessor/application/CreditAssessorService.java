package br.com.microservices.creditassessor.application;

import br.com.microservices.creditassessor.application.exception.ClientDataNotFoundException;
import br.com.microservices.creditassessor.application.exception.ComunicationErrorMicroserviceException;
import br.com.microservices.creditassessor.domain.*;
import br.com.microservices.creditassessor.infrastructure.CardsResourceClient;
import br.com.microservices.creditassessor.infrastructure.ClientResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditAssessorService {

    private final ClientResourceClient resourceClient;
    private final CardsResourceClient cardsResourceClient;

    public ClientSituation getClientSituation(String cpf) throws ClientDataNotFoundException, ComunicationErrorMicroserviceException{
        try{
            ResponseEntity<ClientData> clientDataResponse = resourceClient.getClientSituation(cpf);
            ResponseEntity<List<ClientCard>> cardsResponse = cardsResourceClient.cardByClients(cpf);
            return ClientSituation.builder()
                    .client(clientDataResponse.getBody())
                    .cards(cardsResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClientDataNotFoundException();
            }
            throw new ComunicationErrorMicroserviceException(e.getMessage(), status);
        }
    }

    @PostMapping
    public ClientEvaluation makeEvaluation(String cpf, Long income) throws ClientDataNotFoundException, ComunicationErrorMicroserviceException{
        try{
            ResponseEntity<ClientData> clientDataResponse = resourceClient.getClientSituation(cpf);
            ResponseEntity<List<Card>> cardsResponse = cardsResourceClient.findByIncomeLessThanEqual(income);

            List<Card> cards = cardsResponse.getBody();
            var list = cards.stream().map(card -> {
                BigDecimal baseLimit = card.getBaseLimit();
                BigDecimal ageDB = BigDecimal.valueOf(clientDataResponse.getBody().getAge());
                var factor = ageDB.divide(BigDecimal.valueOf(10));
                BigDecimal approvedLimit = factor.multiply(baseLimit);

                ApprovedCard approved = new ApprovedCard();
                approved.setCard(card.getName());
                approved.setBrand(card.getBrand());
                approved.setApprovedLimit(approvedLimit);

                return approved;
            }).collect(Collectors.toList());

            return new ClientEvaluation(list);
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClientDataNotFoundException();
            }
            throw new ComunicationErrorMicroserviceException(e.getMessage(), status);
        }
    }

}
