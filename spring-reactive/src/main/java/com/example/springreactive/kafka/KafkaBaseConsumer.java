package com.example.springreactive.kafka;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import reactor.core.publisher.Mono;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class KafkaBaseConsumer {
    private final ReceiverOptions<String, EventDTO> receiverOptions;
    private final KafkaProducerProperties kafkaProducerProperties;
    private final DeadLetterPublishingRecoverer deadLetterPublishingRecoverer;

    private List<String> topic;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaBaseConsumer(ReceiverOptions<String, EventDTO> receiverOptions, KafkaProducerProperties kafkaProducerProperties, List<String> topic) {
        this.receiverOptions = receiverOptions;
        this.kafkaProducerProperties = kafkaProducerProperties;
        this.topic = topic;
        this.deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(
                getEventKafkaTemplate(), (record, ex) -> new TopicPartition(record.topic() + "-dlt", -1));
    }

    public KafkaBaseConsumer(ReceiverOptions<String, EventDTO> receiverOptions, KafkaProducerProperties kafkaProducerProperties, String groupId, List<String> topic) {
        Map<String, Object> configProperties = receiverOptions.consumerProperties();
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //
        this.receiverOptions = initOptions(configProperties);
        this.kafkaProducerProperties = kafkaProducerProperties;
        this.topic = topic;
        this.deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(
                getEventKafkaTemplate(), (record, ex) -> new TopicPartition(record.topic() + "-dlt", -1));
    }

    protected abstract Mono<Void> processMessage(ReceiverRecord<String, EventDTO> message);

    @EventListener(ApplicationStartedEvent.class)
    public void onMessage() {
        KafkaReceiver.create(receiverOptions.subscription(topic))
                .receive()
                .doOnNext(record -> {
                    log.info("Receiver message: {} - offset: {}", record.value(), record.offset());
                    record.receiverOffset().acknowledge();
                })
                .concatMap(message ->
                        processMessage(message)
                                .doOnError(error -> log.error("An error occurred while processing the message from the topic RETRY: key - {}, errorMessage - {}",
                                        message.key(), error))
                                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)).transientErrors(true))
                                .onErrorResume(e -> {
                                    log.error("Retries exhausted for: {}", message.value());
                                    deadLetterPublishingRecoverer.accept(message, new Exception(e.getMessage()));
                                    return Mono.empty();
                                })
                ).subscribe();
    }

    private KafkaOperations<String, Object> getEventKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProducerProperties.buildKafkaProducerConfig()));
    }

    private ReceiverOptions<String, EventDTO> initOptions(Map<String, Object> map) {
        final JsonDeserializer<EventDTO> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        ReceiverOptions<String, EventDTO> options = ReceiverOptions.create(map);
        return options
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(jsonDeserializer);
    }
}
