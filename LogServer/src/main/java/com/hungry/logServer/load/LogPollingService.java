package com.hungry.logServer.load;

import com.hungry.logServer.data.SudoLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogPollingService {

    private final SudoLogRepository sudoLogRepository;
    private String lastTimestamp = "2024-12-10T11:00:00.000Z"; // 초기 값 설정

    public LogPollingService(SudoLogRepository sudoLogRepository) {
        this.sudoLogRepository = sudoLogRepository;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void pollNewLogs() {
        List<SudoLog> newLogs = sudoLogRepository.fetchNewLogs(lastTimestamp);
        if (!newLogs.isEmpty()) {
            lastTimestamp = newLogs.get(newLogs.size() - 1).getTimestamp();
            newLogs.forEach(log -> System.out.println("New Log: " + log));
        }
    }
}

