package br.com.microservices.creditassessor.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queue.cards-post}")
    private String requestCardQueue;

    @Bean
    public Queue queueRequestCard(){
        return new Queue(requestCardQueue, true);
    }
}
