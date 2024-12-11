package com.hungry.logServer.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "parsed-sudo-logs")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }

    private void sendDiscordAlert(String content) {

    }
}
