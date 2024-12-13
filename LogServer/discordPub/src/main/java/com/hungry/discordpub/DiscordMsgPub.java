package com.hungry.discordpub;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class DiscordMsgPub implements AcknowledgingMessageListener<String, String> {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    @Override
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
        int ret = pubMsg(data.value());

        if (ret == 0){
            acknowledgment.acknowledge(); // 메시지 처리 완료 후 오프셋 커밋
        }
    }

    public int pubMsg(String msg) {
        Map<String, String> payload = new HashMap<>();
        payload.put("content", msg);

        int maxRetries = 5; // 최대 재시도 횟수
        int delayMillis = 1000; // 재시도 간격 (1초)

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                restTemplate.postForEntity(discordWebhookUrl, payload, String.class);
                System.out.println("Discord alert sent successfully!");
                return 0; // 성공 시 반환
            } catch (Exception e) {
                System.err.println("Attempt " + attempt + " failed: " + e.getMessage());

                // Rate limit을 처리 (Discord의 429 응답 처리)
                if (e.getMessage().contains("429 Too Many Requests")) {
                    System.err.println("Rate limit reached. Retrying after delay...");
                }

                // 최대 재시도 횟수를 초과하면 실패 처리
                if (attempt == maxRetries) {
                    System.err.println("Max retries reached. Failed to send Discord alert.");
                    return -1; // 실패 시 반환
                }

                // 딜레이 후 재시도
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException ie) {
                    System.err.println("Retry interrupted: " + ie.getMessage());
                    Thread.currentThread().interrupt();
                    return -1;
                }
            }
        }

        return -1; // 모든 재시도 실패 시 반환
    }
}
