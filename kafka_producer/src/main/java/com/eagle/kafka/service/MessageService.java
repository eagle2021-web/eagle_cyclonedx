package com.eagle.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class MessageService {

    public static final String TOPIC2 = "gen2";
    public static final String TOPIC1 = "gen1";
    @Resource
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MessageService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(String key, String message) {
        if (Objects.nonNull(key)) {
            // 发送消息
            this.kafkaTemplate.send(TOPIC2, key, message);
        } else {
            // 发送不带key的消息
            this.kafkaTemplate.send(TOPIC2, message);
        }
    }

    public void partition(Integer partition) {
        this.kafkaTemplate.send(TOPIC2, partition, "message", "value");
        log.info("分区｛｝", partition);
    }
}
