package com.hungry.logServer.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungry.logServer.Alert.DiscordAlerter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.hungry.logServer.Command.CommandLog;

@Service
public class KafkaConsumerService {

    private DiscordAlerter discordAlerter;
    private ObjectMapper objectMapper;
    //private final Logger logger;

    public KafkaConsumerService(DiscordAlerter discordAlerter, ObjectMapper objectMapper) {
        this.discordAlerter = discordAlerter;
        this.objectMapper = objectMapper;
        //this.logger = logger;
    }

    @KafkaListener(topics = "parsed-sudo-logs")
    public void listen(String message) {
        sendDiscordAlert(message);
    }

    @KafkaListener(topics = "audit-sudo-pipeline")
    public void consumeMessage(CommandLog commandLog) {
        //logger.info(commandLog.toString());
        System.out.println(commandLog.toString());
    }

    private void sendDiscordAlert(String content) {
        discordAlerter.sendDiscordAlert(content);
    }
}
