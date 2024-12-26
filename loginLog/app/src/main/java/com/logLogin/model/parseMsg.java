package com.logLogin.model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class parseMsg {
    @JsonProperty("ip")
    private List<String> ip;
    @JsonProperty("source_ip")
    private String sourceIp;
    @JsonProperty("login_id")
    private String loginId;
    @JsonProperty("result")
    private String result;
    @JsonProperty("hostname")
    private String hostname;
    @JsonProperty("@timestamp")
    private String timestamp;

    @Override
    public String toString() {
        return "Login Info [login_id=" + loginId + 
           ", source_ip=" + sourceIp + 
           ", ip=" + ip + 
           ", result=" + result + 
           ", hostname=" + hostname + 
           ", timestamp=" + timestamp + "]";
    }
    public String discordMsg(){
        StringBuilder message = new StringBuilder();

        if(result.equals("success")){
            
            message.append("```ansi\\n")
                .append("[\\u001B[1;36mSUCCESS\\u001B[0m] '").append("\\u001B[1m")
                .append(loginId).append("\\u001B[0m' connected from \\u001B[4;34m")
                .append(sourceIp).append("\\u001B[0m to \\u001B[1m").append(hostname).append("\\u001B[0m(").append(ip).append(").\\n")
                .append("```");            
        }
        else{
            message.append("```ansi\\n")
                .append("[\\u001B[1;31mFAILED\\u001B[0m] '").append("\\u001B[1m")
                .append(loginId).append("\\u001B[0m' failed to connect from \\u001B[4;34m")
                .append(sourceIp).append("\\u001B[0m to \\u001B[1m").append(hostname).append("\\u001B[0m(").append(ip).append(").\\n")
                .append("```");
        }
        return message.toString();
    }

}
