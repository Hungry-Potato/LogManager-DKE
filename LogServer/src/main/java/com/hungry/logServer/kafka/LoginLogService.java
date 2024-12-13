package com.hungry.logServer.kafka;

import org.springframework.stereotype.Service;
import com.hungry.logServer.Elasticsearch.LoginLogRepository;
import com.hungry.logServer.Model.LoginLog;


@Service
public class LoginLogService {

    private final LoginLogRepository loginLogRepository;

    public LoginLogService(LoginLogRepository loginLogRepository) {
        this.loginLogRepository = loginLogRepository;
    }

    public LoginLog getMostRecentLog(String tty, String loginId, String hostname) {
        return loginLogRepository.findFirstByQuery(tty, loginId, hostname).orElse(null);
    }
}
