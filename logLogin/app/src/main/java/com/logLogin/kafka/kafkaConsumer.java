package com.logLogin.kafka;
import com.logLogin.model.parseMsg;
import com.logLogin.alert.discordAlerter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class kafkaConsumer {
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "audit-login-pipeline")
    public void consumeMessage(String msg){
        try {
            parseMsg alertMessage = objectMapper.readValue(msg, parseMsg.class);
            //System.out.println(alertMessage.discordMsg());
            discordAlerter.sendDiscordMessage(alertMessage.discordMsg());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
}
