package com.hungry.logServer.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandLog {
    @JsonProperty("@timestamp")
    private String timestamp;
    @JsonProperty("user-permission")
    private String userPermission;

    private String command;
    private String pwd;
    private String tty;

    private List<String> ip;
    private String ses;
    private String hostname;
    private String user;
    private String sourceIp;

    @Override
    public String toString() {
        return "CommandLog{" +
                "timestamp='" + timestamp + '\'' +
                ", userPermission='" + userPermission + '\'' +
                ", command='" + command + '\'' +
                ", pwd='" + pwd + '\'' +
                ", tty='" + tty + '\'' +
                ", ip=" + ip +
                ", ses='" + ses + '\'' +
                ", hostname='" + hostname + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public void setTimestamp(String timestamp) {
        // UTC 타임스탬프를 Instant로 변환
        Instant instant = Instant.parse(timestamp);

        // Instant를 KST (Asia/Seoul) 로컬 시간으로 변환
        LocalDateTime kstDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Seoul"));

        // "년", "월", "일", "시", "분", "초" 형식으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
        this.timestamp = kstDateTime.format(formatter);
    }

    private String toDiscordMessageTty() {
        StringBuilder message = new StringBuilder();
        message.append("```") // 코드 블록 시작
                .append("📋 Command Log\n")
                .append("🕒 Timestamp       : ").append(timestamp).append("\n")
                .append("👤 User            : ").append(user).append(" (").append(userPermission).append(")\n")
                .append("💻 Hostname        : ").append(hostname).append(" (").append(ip).append(")\n")
                .append("📂 Working Dir     : ").append(pwd).append("\n")
                .append("📜 Command         : ").append(command).append("\n")
                .append("🖥️ TTY             : ").append(tty).append("\n")
                //.append("🌐 IP Addresses    : ").append(ip != null ? String.join(", ", ip) : "N/A").append("\n")
                .append("🆔 Session ID      : ").append(ses).append("\n")
                .append("```"); // 코드 블록 끝

        return message.toString();
    }

    private String toDiscordMessageIp() {
        StringBuilder message = new StringBuilder();
        message.append("```") // 코드 블록 시작
                .append("📋 Command Log\n")
                .append("🕒 Timestamp       : ").append(timestamp).append("\n")
                .append("👤 User            : ").append(user).append(" (").append(userPermission).append(")\n")
                .append("💻 Hostname        : ").append(hostname).append(" (").append(ip).append(")\n")
                .append("📂 Working Dir     : ").append(pwd).append("\n")
                .append("📜 Command         : ").append(command).append("\n")
                .append("🌐 IP Address      : ").append(sourceIp).append("\n")
                //.append("🌐 IP Addresses    : ").append(ip != null ? String.join(", ", ip) : "N/A").append("\n")
                .append("🆔 Session ID      : ").append(ses).append("\n")
                .append("```"); // 코드 블록 끝

        return message.toString();
    }

    public String toDiscordMessage() {
        if (this.sourceIp != null){
            return toDiscordMessageIp();
        }
        return toDiscordMessageTty();
    }
}
