package br.com.microservices.creditassessor.application.exception;

public class ClientDataNotFoundException extends Exception{

    public ClientDataNotFoundException() {
        super("Client Data not found in the CPF informed");
    }
}
