package com.hungry.logServer.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungry.logServer.Alert.DiscordAlerter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hungry.logServer.Model.CommandLog;

import java.io.IOException;

@Service
public class KafkaConsumerService {

    private final DiscordAlerter discordAlerter;
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(DiscordAlerter discordAlerter, ObjectMapper objectMapper) {
        this.discordAlerter = discordAlerter;
        this.objectMapper = objectMapper;
        //this.logger = logger;
    }

    @KafkaListener(topics = "parsed-sudo-logs")
    public void listen(String message) {
        //sendDiscordAlert(message);
    }

    @KafkaListener(topics = "audit-sudo-pipeline")
    public void consumeMessage(String msg) {
        try {
            CommandLog commandLog = objectMapper.readValue(msg, CommandLog.class);
            discordAlerter.sendDiscordAlert(commandLog.toDiscordMessage());
            // 필요한 로직 추가
            System.out.println(commandLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendDiscordAlert(String content) {
        discordAlerter.sendDiscordAlert(content);
    }
}
