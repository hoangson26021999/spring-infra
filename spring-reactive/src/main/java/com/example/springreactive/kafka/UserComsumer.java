package com.example.springreactive.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;

@Slf4j
@Component
public class UserComsumer extends KafkaBaseConsumer {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public UserComsumer(ReceiverOptions<String, EventDTO> receiverOptions,
                        KafkaProducerProperties kafkaProducerProperties,
                        @Value("${spring.kafka.consumer.topic.moderated-user}") String moderatedUser) {
        super(receiverOptions, kafkaProducerProperties, Collections.singletonList(moderatedUser));
    }

    @Override
    protected Mono<Void> processMessage(ReceiverRecord<String, EventDTO> message) {
        log.info("processMessage: {}", message);

        return Mono.just(message)
            .map(ConsumerRecord::value)
            .switchIfEmpty(Mono.error(new Exception("EventDTO null"))).then();
    }

}
