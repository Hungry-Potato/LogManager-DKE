package com.hungry.logServer.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.producer.topic-name:default-topic}")
    private String topicName;


    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String value) {
        kafkaTemplate.send(topicName, value);
    }


}
