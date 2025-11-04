package com.example.springreactive.kafka;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ReactiveKafkaConsumerConfig {
    private final Environment env;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String boostrapServer;

    @Value(value = "${spring.kafka.sasl.mechanism}")
    private String saslMechanism;

    @Value(value = "${spring.kafka.sasl.jaas.config.user}")
    private String saslJaasCfgUser;

    @Value(value = "${spring.kafka.sasl.jaas.config.pass}")
    private String saslJaasCfgPass;

    @Value(value = "${spring.kafka.security.protocol}")
    private String securityProtocol;

    @Value(value = "${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value(value = "${spring.kafka.consumer.max-poll-interval-ms}")
    private Integer maxPollIntervalMs;

    public void show() {
        log.info("kafka-consumer-boostrapServer {}", this.boostrapServer);
        log.info("kafka-consumer-saslMechanism {}", this.saslMechanism);
        log.info("kafka-consumer-saslJaasCfgUser {}", this.saslJaasCfgUser);
        log.info("kafka-consumer-securityProtocol {}", this.securityProtocol);
        log.info("kafka-consumer-auto_offset_reset {}", this.autoOffsetReset);
        log.info("kafka-consumer-maxPollIntervalMs {}", this.maxPollIntervalMs);
    }

    @Bean
    public Map<String, Object> kafkaConsumerConfiguration() {
        this.show();
        String authen = "org.apache.kafka.common.security.plain.PlainLoginModule required "
                + "username=\"" + saslJaasCfgUser + "\""
                + " password=\"" + saslJaasCfgPass + "\";";

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, authen);
        props.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);

        return props;
    }

    private <V> ReceiverOptions<String, V> initOptions(Map<String, Object> map) {
        // Json serialization configuration
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.findAndRegisterModules();

        final JsonDeserializer<V> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        ReceiverOptions<String, V> options = ReceiverOptions.create(map);
        return options
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(jsonDeserializer);
    }

    @Bean
    @Primary
    <V> ReceiverOptions<String, V> kafkaReceiverOptions() {
        return initOptions(kafkaConsumerConfiguration());
    }

    @Bean
    <V> ReceiverOptions<String, V> kafkaPodOptions() {
        val map = kafkaConsumerConfiguration();
        map.put(ConsumerConfig.GROUP_ID_CONFIG, groupId + '.' + env.getProperty("HOSTNAME"));
        return initOptions(map);
    }
}
