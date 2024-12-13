package com.logLogin.alert;

import java.io.OutputStream;
import java.net.HttpURLConnection;

public class discordAlerter {
    private static final String WEBHOOK_URL = "Discord WebHook URL";

    public static void sendDiscordMessage(String message) {
        try {
            URL url = new URL(WEBHOOK_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonPayload = "{\"content\": \"" + message + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Message sent successfully to Discord");
            } else {
                System.err.println("Failed to send message to Discord. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error sending message to Discord: " + e.getMessage());
        }
    }
}
