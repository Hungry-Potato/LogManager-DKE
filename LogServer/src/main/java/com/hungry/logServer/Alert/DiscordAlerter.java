package com.hungry.logServer.Alert;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Bean
public class DiscordAlerter {
    private final RestTemplate restTemplate;

    public DiscordAlerter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendDiscordAlert(String message) {
        Map<String, String> payload = new HashMap<>();
        payload.put("content", content);

        try {
            restTemplate.postForEntity(discordWebhookUrl, payload, String.class);
            System.out.println("Discord alert sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send Discord alert: " + e.getMessage());
        }
    }
}
