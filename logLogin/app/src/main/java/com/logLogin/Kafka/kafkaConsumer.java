package com.logLogin.Kafka;
//import com.logLogin.Model.commandLog;
//import java.io.IOException;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

@Component
public class kafkaConsumer {
    //private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "audit-login-pipeline")
    public void consumeMessage(String msg){
        //try {
        System.out.println(msg);
           // commandLog cL = objectMapper.readValue(msg, commandLog.class);
            //discordAlerter.sendDiscordAlert(commandLog.toDiscordMessage());
            // 필요한 로직 추가
            //System.out.println(cL);
        //} catch (IOException e) {
         //   e.printStackTrace();
        //}
    }    
}
