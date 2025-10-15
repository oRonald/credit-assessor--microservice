package br.com.microservices.creditassessor.infrastructure.mqueue;

import br.com.microservices.creditassessor.domain.RequestCardData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardsPostPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueCardsPost;

    public void requestCard(RequestCardData requestCard) throws JsonProcessingException {
        var json = convertIntoJSON(requestCard);
        rabbitTemplate.convertAndSend(queueCardsPost.getName(), json);
    }

    private String convertIntoJSON(RequestCardData cardPostData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(cardPostData);
    }

}
