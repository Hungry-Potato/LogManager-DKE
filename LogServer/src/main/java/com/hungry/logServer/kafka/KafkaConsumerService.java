package com.hungry.logServer.kafka;


import com.hungry.logServer.Alert.DiscordAlerter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private DiscordAlerter discordAlerter;

    public KafkaConsumerService(DiscordAlerter discordAlerter) {
        this.discordAlerter = discordAlerter;
    }

    @KafkaListener(topics = "parsed-sudo-logs")
    public void listen(String message) {
        sendDiscordAlert(message);
    }

    private void sendDiscordAlert(String content) {
        discordAlerter.sendDiscordAlert(content);
    }
}
