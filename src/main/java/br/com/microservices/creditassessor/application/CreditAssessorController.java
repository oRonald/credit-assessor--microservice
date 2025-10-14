package br.com.microservices.creditassessor.application;

import br.com.microservices.creditassessor.domain.ClientSituation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit-assessor")
@RequiredArgsConstructor
public class CreditAssessorController {

    private final CreditAssessorService service;

    @GetMapping
    public String status(){
        return "ok";
    }

    @GetMapping(value = "client-situation", params = "cpf")
    public ResponseEntity<ClientSituation> checkClientSituation(@RequestParam String cpf){
        ClientSituation situation = service.getClientSituation(cpf);
        return ResponseEntity.ok(situation);
    }
}
