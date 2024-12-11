package com.hungry.logServer.data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "parsed-sudo-logs")
public class SudoLog {
    @Id
    private String id; // Elasticsearch 문서 ID

    private String user;
    private String command;
    private String timestamp;

    // Getter와 Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
