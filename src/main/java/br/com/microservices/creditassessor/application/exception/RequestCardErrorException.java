package br.com.microservices.creditassessor.application.exception;

public class RequestCardErrorException extends RuntimeException{

    public RequestCardErrorException(String message) {
        super(message);
    }
}
