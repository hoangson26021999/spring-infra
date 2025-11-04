package com.example.springreactive.kafka;

import lombok.Data;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class KafkaProducerProperties {

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

    public Map<String, Object> buildKafkaProducerConfig() {
        String authen = "org.apache.kafka.common.security.plain.PlainLoginModule required "
                + "username=\"" + saslJaasCfgUser + "\""
                + " password=\"" + saslJaasCfgPass + "\";";

        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, authen);
        props.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, securityProtocol);

        return props;
    }
}
