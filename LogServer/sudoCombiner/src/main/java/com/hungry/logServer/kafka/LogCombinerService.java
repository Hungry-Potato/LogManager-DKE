package com.hungry.logServer.kafka;

import org.springframework.stereotype.Service;
import com.hungry.logServer.Model.CommandLog;
import com.hungry.logServer.Model.LoginLog;

@Service
public class LogCombinerService {
    private final KafkaProducerService kafkaProducerService;
    private final LoginLogService logService;

    public LogCombinerService(KafkaProducerService kafkaProducerService, LoginLogService logService){
        this.kafkaProducerService = kafkaProducerService;
        this.logService = logService;
    }

    public void combineLog(CommandLog commandLog){
        LoginLog loginLog = logService.getMostRecentLog(commandLog.getTty(), commandLog.getUser(), commandLog.getHostname());
        if (loginLog != null && loginLog.getSource_ip() != null){
            commandLog.setSourceIp(loginLog.getSource_ip());
        }
        kafkaProducerService.sendMessage(commandLog.toDiscordMessage());
    }

}
