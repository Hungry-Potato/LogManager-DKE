package com.logLogin.Kafka;
import com.logLogin.Model.parseMsg;
//import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Component
public class kafkaConsumer {
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "audit-login-pipeline")
    public void consumeMessage(String msg){
        try {
            parseMsg alertMessage = objectMapper.readValue(msg, parseMsg.class);
            System.out.println(alertMessage);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
}
