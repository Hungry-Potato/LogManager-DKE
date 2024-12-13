package com.hungry.discordpub;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class DiscordMsgListener implements AcknowledgingMessageListener<String, String> {
    @Override
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
        System.out.println("Received: " + data.value());
        acknowledgment.acknowledge(); // 메시지 처리 완료 후 오프셋 커밋
    }
}
