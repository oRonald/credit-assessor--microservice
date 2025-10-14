package br.com.microservices.creditassessor.application;

import br.com.microservices.creditassessor.application.exception.ClientDataNotFoundException;
import br.com.microservices.creditassessor.application.exception.ComunicationErrorMicroserviceException;
import br.com.microservices.creditassessor.domain.ClientSituation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity checkClientSituation(@RequestParam String cpf){
        try {
            ClientSituation situation = service.getClientSituation(cpf);
            return ResponseEntity.ok(situation);
        } catch (ClientDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ComunicationErrorMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}
