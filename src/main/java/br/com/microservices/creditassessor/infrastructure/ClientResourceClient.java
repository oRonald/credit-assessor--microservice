package br.com.microservices.creditassessor.infrastructure;

import br.com.microservices.creditassessor.domain.ClientData;
import br.com.microservices.creditassessor.domain.ClientSituation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "clients", path = "/clients")
public interface ClientResourceClient {

    @GetMapping
    ResponseEntity<ClientData> getClientSituation(@RequestParam String cpf);
}
