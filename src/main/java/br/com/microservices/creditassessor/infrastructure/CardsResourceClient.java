package br.com.microservices.creditassessor.infrastructure;

import br.com.microservices.creditassessor.domain.Card;
import br.com.microservices.creditassessor.domain.ClientCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "cards", path = "/cards")
public interface CardsResourceClient {

    @GetMapping(path = "/by-cpf")
    public ResponseEntity<List<ClientCard>> cardByClients(@RequestParam String cpf);

    @GetMapping(path = "/by-income")
    ResponseEntity<List<Card>> findByIncomeLessThanEqual(@RequestParam Long income);
}
