package com.hungry.logServer.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hungry.logServer.Model.CommandLog;

import java.io.IOException;

@Service
public class KafkaConsumerService {
    private final LogCombinerService logCombinerService;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(LogCombinerService logCombinerService, ObjectMapper objectMapper) {
        this.logCombinerService = logCombinerService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topicname}")
    public void consumeMessage(String msg) {
        try {
            CommandLog commandLog = objectMapper.readValue(msg, CommandLog.class);
            logCombinerService.combineLog(commandLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
