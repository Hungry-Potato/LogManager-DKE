package com.hungry.logServer.Command;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandLog {
    @JsonProperty("pwd")
    private String pwd;

    @JsonProperty("command")
    private String command;

    @JsonProperty("@timestamp")
    private String timestamp;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("user")
    private String user;

    @JsonProperty("user-permission")
    private String userPermission;

    @JsonProperty("tty")
    private String tty;

    @JsonProperty("ses")
    private String ses;

    @JsonProperty("ip")
    private List<String> ip;

    @Override
    public String toString() {
        return "AuditLog{" +
                "pwd='" + pwd + '\'' +
                ", command='" + command + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", hostname='" + hostname + '\'' +
                ", user='" + user + '\'' +
                ", userPermission='" + userPermission + '\'' +
                ", tty='" + tty + '\'' +
                ", ses='" + ses + '\'' +
                ", ip=" + ip +
                '}';
    }
}
