package com.example.springreactive.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Slf4j
@Configuration(value = "reactiveKafkaProducerConfig")
public class ReactiveKafkaProducerConfig {

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    @Bean
    public <V> KafkaSender<String, V> kafkaSenderConfig() {
        return KafkaSender.create(createSenderOptions());
    }

    private <V> SenderOptions<String, V> createSenderOptions() {
        return SenderOptions.<String, V>create(kafkaProducerProperties.buildKafkaProducerConfig()).maxInFlight(1024)
                .withKeySerializer(new StringSerializer())
                .withValueSerializer(new JsonSerializer<V>());
    }
}
