package com.cloudmore.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaConfigurationProperties properties;

    @Bean
    public NewTopic kafkaTopic() {
        return TopicBuilder
            .name(properties.getTopic())
            .partitions(1)
            .replicas(1)
            .compact().build();
    }
}
