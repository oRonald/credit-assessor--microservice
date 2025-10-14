package br.com.microservices.creditassessor.application;

import br.com.microservices.creditassessor.application.exception.ClientDataNotFoundException;
import br.com.microservices.creditassessor.application.exception.ComunicationErrorMicroserviceException;
import br.com.microservices.creditassessor.domain.ClientEvaluation;
import br.com.microservices.creditassessor.domain.EvaluateData;
import br.com.microservices.creditassessor.domain.ClientSituation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity makeAssessment(@RequestBody EvaluateData data){
        try {
            ClientEvaluation clientEvaluation = service.makeEvaluation(data.getCpf(), data.getIncome());
            return ResponseEntity.ok(clientEvaluation);
        } catch (ClientDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ComunicationErrorMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}
