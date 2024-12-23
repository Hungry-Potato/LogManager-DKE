package com.logLogin.kafka;
import com.logLogin.model.parseMsg;
import com.logLogin.alert.discordAlerter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;


@Component
public class kafkaConsumer {
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "audit-login-pipeline")
    public void consumeMessage(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String msg = record.value();
        try {
            parseMsg alertMessage = objectMapper.readValue(msg, parseMsg.class);

            boolean success = discordAlerter.sendDiscordMessage(alertMessage.discordMsg());
            int retryCount = 0;
            int maxRetries = 3;

            while (!success && retryCount < maxRetries) {
                retryCount++;
                System.err.println("Retrying to send Discord message. Attempt: " + retryCount);
                success = discordAlerter.sendDiscordMessage(alertMessage.discordMsg());
                Thread.sleep(1000);
            }

            if (success) {
                ack.acknowledge();
            } else {
                System.err.println("Failed to send message to Discord after " + maxRetries + " attempts.");
                throw new RuntimeException("Discord message delivery failed.");
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
