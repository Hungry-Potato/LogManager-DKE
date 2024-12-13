package com.hungry.logServer.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Document(indexName = "auditbeat-login-logs-*") // Elasticsearch 인덱스 패턴
public class LoginLog {

    @Id
    private String id; // Elasticsearch 문서 ID

    private String loginId; // 로그인 ID
    private String method; // 로그인 방법 (예: /usr/sbin/sshd)
    private String ip; // 내부 IP
    private String status; // 로그인 상태 (예: user_login)
    private String result; // 결과 (예: success, failure)
    private String source_ip; // 외부 IP
    private String hostname; // 호스트 이름
    private String tty; // 터미널 유형
    private Instant timestamp; // 로그 발생 시간
}
