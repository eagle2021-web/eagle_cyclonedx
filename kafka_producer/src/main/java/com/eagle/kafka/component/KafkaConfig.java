package com.eagle.kafka.component;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic topic1() {
        return new NewTopic("gen1", 2, (short) 0);
    }
}
