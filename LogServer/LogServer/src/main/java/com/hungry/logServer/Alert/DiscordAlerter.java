package com.hungry.logServer.Alert;

import com.hungry.logServer.Model.CommandLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscordAlerter {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    public void sendDiscordAlert(String message) {
        Map<String, String> payload = new HashMap<>();
        payload.put("content", message);

        try {
            restTemplate.postForEntity(discordWebhookUrl, payload, String.class);
            System.out.println("Discord alert sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send Discord alert: " + e.getMessage());
        }
    }

    public void sendDiscordAlert(CommandLog commandLog) {
        Map<String, String> payload = new HashMap<>();
        payload.put("command", commandLog.toDiscordMessage());

        try {
            restTemplate.postForEntity(discordWebhookUrl, payload, String.class);
            System.out.println("Discord alert sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send Discord alert: " + e.getMessage());
        }
    }
}
